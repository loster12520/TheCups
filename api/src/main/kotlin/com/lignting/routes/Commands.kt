package com.lignting.routes

import com.lignting.data.models.CommandWebSocketData
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send

fun Application.commandsRouting() {
    routing {
        webSocket("/echo") {
            send("Please enter your name")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                } else {
                    send(Frame.Text("Hi, $receivedText!"))
                }
            }
        }
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