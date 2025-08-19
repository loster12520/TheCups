package com.lignting.com.lignting.routes

import com.lignting.com.lignting.data.models.User
import com.lignting.com.lignting.utils.JwtUtils
import com.lignting.com.lignting.utils.call
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.usersRouting() {
    routing {
        post("/users/login") {
            call.receive<User>().let {
                JwtUtils.jwt(it.account)
            }.also {
                call.respond(it)
            }
        }
        
        authenticate {
            get("/test/1111") {
                call {
                    "OK"
                }
            }
        }
    }
}