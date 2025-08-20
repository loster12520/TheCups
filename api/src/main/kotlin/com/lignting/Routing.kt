package com.lignting

import com.lignting.routes.commandsRouting
import com.lignting.routes.usersRouting
import io.ktor.server.application.*

/**
 * Configures the routing for the Ktor application.
 */
fun Application.configureRouting() {
    commandsRouting()
    usersRouting()
}