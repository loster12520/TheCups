package com.lignting.data.models

data class CommandWebSocketData(
    /**
     * The type of data, including: 'message', 'heartbeat', 'command'
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
)