package com.lignting.routes

import com.lignting.data.models.CommandWebSocketData
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket

fun Application.commandsRouting() {
    routing {
        webSocket("/command/test") {
            val input = receiveDeserialized<CommandWebSocketData>()
            when (input.type) {
                // 心跳回包
                "heartbeat" -> {
                    sendSerialized(
                        CommandWebSocketData(
                            "heartbeat",
                            System.currentTimeMillis(),
                            "感受到主人的心跳了喵~"
                        )
                    )
                }
            }
        }
    }
}