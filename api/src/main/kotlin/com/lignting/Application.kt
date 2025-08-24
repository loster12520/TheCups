package com.lignting

import com.lignting.utils.JwtUtils
import io.ktor.serialization.gson.GsonWebsocketContentConverter
import io.ktor.serialization.gson.gson
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    
    install(Authentication) {
        jwt {
            realm = "lignting"
            verifier(JwtUtils.getVerifier())
            validate { credentials ->
                val account = credentials.payload.subject
                if (account != null) {
                    JWTPrincipal(credentials.payload)
                } else {
                    null
                }
            }
        }
    }
    
    install(WebSockets) {
        pingPeriod = 5.seconds
        timeout = 10.seconds
        maxFrameSize = 1000
        masking = false
        contentConverter = GsonWebsocketContentConverter()
    }
    
    configureRouting()
}