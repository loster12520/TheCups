package com.lignting

import com.lignting.com.lignting.utils.JwtUtils
import io.ktor.serialization.gson.gson
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authentication
import io.ktor.server.auth.basic
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

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
    
    configureRouting()
}