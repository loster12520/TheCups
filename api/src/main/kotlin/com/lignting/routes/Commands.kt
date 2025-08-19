package com.lignting.com.lignting.routes

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket

fun Application.commandsRouting() {
    routing {
        webSocket {
            
        }
    }
}