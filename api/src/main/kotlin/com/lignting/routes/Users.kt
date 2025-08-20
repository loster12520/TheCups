package com.lignting.routes

import com.lignting.data.models.User
import com.lignting.utils.JwtUtils
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.usersRouting() {
    routing {
        /**
         * 用户登录
         *
         * 接收一个 User 对象，包含账号和密码，
         * 返回一个 JWT 令牌.
         */
        post("/users/login") {
            call.receive<User>().let {
                JwtUtils.jwt(it.account)
            }.also {
                call.respond(it)
            }
        }
    }
}