package com.lignting.routes

import com.lignting.data.models.CommandWebSocketData
import com.lignting.services.CommandService
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * 会话信息
 * @param token 用户的唯一标识符
 * @param commandService 命令执行服务实例
 * @param lastActive 上次活跃时间戳
 * @param traceId 当前执行命令的traceId
 */
data class SessionInfo(
    val token: String,
    val commandService: CommandService,
    var lastActive: Long,
    var traceId: String = ""
)

/**
 * 对话池，用于存储用户的会话信息
 */
val sessionCache = ConcurrentHashMap<String, SessionInfo>()

fun Application.commandsRouting() {
    // 定时清理3分钟未活跃的session
    launch {
        while (true) {
            val now = System.currentTimeMillis()
            sessionCache.entries.removeIf { now - it.value.lastActive > 3 * 60 * 1000 }
            delay(60 * 1000)
        }
    }
    
    routing {
        webSocket("/command/test") {
            var authed = false
            var token = ""
            var traceId = ""
            var sessionInfo: SessionInfo? = null
            
            // 认证超时定时器
            val authTimeout = launch {
                delay(20_000)
                if (!authed) {
                    println("认证超时，关闭连接")
                    close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "未认证"))
                }
            }
            
            try {
                for (frame in incoming) {
                    val input = receiveDeserialized<CommandWebSocketData>().also {
                        println(it)
                    }
                    
                    when (input.type) {
                        "auth" -> {
                            token = input.data ?: ""
                            // 这里应校验token有效性
                            authed = true
                            authTimeout.cancel()
                            // 获取或新建SessionInfo
                            sessionInfo = sessionCache.computeIfAbsent(token) {
                                SessionInfo(
                                    token,
                                    CommandService { msg ->
                                        // 缓存输出
                                        sessionCache[token]?.commandService?.outputMessage?.append(msg)
                                        // 推送消息
                                        sendSerialized(
                                            CommandWebSocketData(
                                                "message",
                                                System.currentTimeMillis(),
                                                msg,
                                                traceId
                                            )
                                        )
                                    },
                                    System.currentTimeMillis()
                                )
                            }
                            sessionInfo.lastActive = System.currentTimeMillis()
                            sendSerialized(
                                CommandWebSocketData("auth", System.currentTimeMillis(), "success", "")
                            )
                        }
                        
                        "heartbeat" -> {
                            if (!authed) continue
                            sessionInfo?.lastActive = System.currentTimeMillis()
                            sendSerialized(
                                CommandWebSocketData("heartbeat", System.currentTimeMillis(), "pong", input.traceId)
                            )
                        }
                        
                        "message" -> {
                            if (!authed) continue
                            sessionInfo?.lastActive = System.currentTimeMillis()
                            traceId = input.traceId ?: ""
                            sessionInfo?.traceId = traceId
                            val command = input.data ?: ""
                            // 如果是重连，补发历史输出
                            if (sessionInfo?.commandService?.isRunning == true) {
                                sendSerialized(
                                    CommandWebSocketData(
                                        "message",
                                        System.currentTimeMillis(),
                                        sessionInfo.commandService.outputMessage.toString(),
                                        traceId
                                    )
                                )
                            } else {
                                // 新命令，清空输出
                                sessionInfo?.commandService?.outputMessage = StringBuffer()
                                val success = sessionInfo?.commandService?.executeCommand(command) ?: false
                                sendSerialized(
                                    CommandWebSocketData(
                                        "message",
                                        System.currentTimeMillis(),
                                        if (success) "命令执行完毕喵~" else "命令执行失败喵~",
                                        traceId
                                    )
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                sendSerialized(
                    CommandWebSocketData(
                        "error",
                        System.currentTimeMillis(),
                        "连接异常: ${e.message}",
                        traceId
                    )
                )
            } finally {
                authTimeout.cancel()
            }
        }
    }
}