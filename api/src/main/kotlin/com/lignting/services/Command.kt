package com.lignting.services

import kotlinx.coroutines.delay
import java.io.File
import kotlin.collections.plusAssign
import kotlin.text.append

/**
 * 命令执行服务
 * @param outputFunction 输出函数，用于将命令输出片段进行处理的回调函数
 */
class CommandService(
    val outputFunction: suspend (String) -> Unit
) {
    /**
     * 命令执行的缓冲区
     */
    var buffer = ""
    
    /**
     * 已发送的输出内容
     */
    var outputMessage = StringBuffer("")
    
    /**
     * 当前工作目录
     */
    var dir = "C:/home" // 默认目录
    
    suspend fun executeCommand(command: String, delay: Long = 100): Boolean {
        buffer = ""
        outputMessage = StringBuffer("")
        
        return try {
            // 使用 ProcessBuilder 启动命令
            val cmd = if (System.getProperty("os.name").startsWith("Windows")) {
                listOf("cmd", "/c") + command.split(" ")
            } else {
                command.split(" ")
            }
            val charset = if (System.getProperty("os.name").startsWith("Windows")) "GBK" else "UTF-8"
            val process = ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .directory(File(dir))
                .start()
            
            val reader = process.inputStream.bufferedReader(charset(charset))
            val buf = CharArray(4096)
            
            while (process.isAlive) {
                val sb = StringBuilder()
                while (reader.ready()) {
                    reader.read(buf)
                        .takeIf { it > 0 }
                        ?.also { sb.append(buf, 0, it) }
                }
                if (sb.isNotEmpty()) {
                    buffer += sb.toString()
                    outputMessage.append(sb)
                    outputFunction(sb.toString())
                }
                delay(delay)
            }
            
            // 进程结束后，读取剩余内容
            var readLen = 0
            while (reader.ready() && reader.read(buf).also { readLen = it } > 0) {
                val str = String(buf, 0, readLen)
                buffer += str
                outputMessage.append(str)
                outputFunction(str)
            }
            
            // 等待进程结束
            process.waitFor()
            process.exitValue() == 0 // 返回 true 表示成功
        } catch (e: Exception) {
            e.printStackTrace()
            false // 异常时返回 false
        }
    }
}