package com.lignting.com.lignting.utils

import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

data class CallResult<T>(
    val code: Int,
    val message: String,
    val data: T?
)

suspend inline fun <T, reified R : Any> RoutingContext.callWithInput(function: RoutingContext.(R) -> T) =
    try {
        function(call.receive<R>()).let {
            call.respond(CallResult(
                code = 200,
                message = "OK",
                data = it
            ))
        }
    } catch (e: Exception) {
        call.respond(CallResult(
            code = 500,
            message = e.message ?: "Internal Server Error",
            data = null
        ))
    }

suspend fun <T> RoutingContext.call(function: RoutingContext.() -> T) =
    try {
        function().let {
            call.respond(CallResult(
                code = 200,
                message = "OK",
                data = it
            ))
        }
    } catch (e: Exception) {
        call.respond(CallResult(
            code = 500,
            message = e.message ?: "Internal Server Error",
            data = null
        ))
    }