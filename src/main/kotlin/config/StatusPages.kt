package com.example.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("errors" to cause.reasons))
        }
    }
}