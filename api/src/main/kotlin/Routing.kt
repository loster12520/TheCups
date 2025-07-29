package com.lignting

import com.lignting.com.lignting.data.DatabaseManager.sqlClient
import com.lignting.com.lignting.data.models.Test
import com.lignting.com.lignting.data.models.fetchBy
import com.lignting.com.lignting.data.models.text
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configures the routing for the Ktor application.
 */
fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/test") {
            sqlClient.createQuery(Test::class) {
                select(
                    table.fetchBy {
                        text()
                    }
                )
            }.execute().also {
                call.respond(it.associate { test -> test.id to test.text })
            }
        }
        post("/test") {
            sqlClient.createUpdate(Test::class) {
                set(table.text, "Hello, Ktor!")
            }.execute().also {
                call.respondText("Test saved with ID: $it")
            }
        }
    }
}