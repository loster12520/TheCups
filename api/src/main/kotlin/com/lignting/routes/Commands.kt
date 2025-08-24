package com.lignting.routes

import com.lignting.data.models.CommandWebSocketData
import com.lignting.services.CommandService
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket

fun Application.commandsRouting() {
    routing {
        webSocket("/command/test") {
            // 当前执行命令的traceId
            var traceId = ""
            val service = CommandService {
                sendSerialized(
                    CommandWebSocketData(
                        "message",
                        System.currentTimeMillis(),
                        it,
                        traceId
                    )
                )
            }
            while (true) {
                val input = receiveDeserialized<CommandWebSocketData>()
                when (input.type) {
                    // 心跳回包
                    "heartbeat" -> {
                        sendSerialized(
                            CommandWebSocketData(
                                "heartbeat",
                                System.currentTimeMillis(),
                                "感受到主人的心跳了喵~",
                                input.traceId
                            )
                        )
                    }
                    "message" -> {
                        input.data?.let { command ->
                            traceId = input.traceId ?: ""
                            val success = service.executeCommand(command)
                            sendSerialized(
                                CommandWebSocketData(
                                    "message",
                                    System.currentTimeMillis(),
                                    if (success) "命令执行完毕喵~" else "命令执行失败喵~",
                                    input.traceId
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}