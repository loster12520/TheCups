package com.lignting.data.models

data class CommandWebSocketData(
    /**
     * The type of data, including: 'message', 'heartbeat', 'close'
     */
    val type: String,
    /**
     * The timestamp of the data, in milliseconds since epoch.
     */
    val timeStamp: Long,
    /**
     * The data content, which can be a message, heartbeat, or command.
     */
    val data: String?,
    /**
     * 用于标识同一命令执行会话的唯一ID
     */
    val traceId: String?
)