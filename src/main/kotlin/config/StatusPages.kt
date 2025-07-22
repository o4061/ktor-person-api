package com.example.config

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.respondText

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<Throwable> { call, cause ->
            call.respondText(text = "500: ${cause.message}", status = HttpStatusCode.InternalServerError)
        }

        status(HttpStatusCode.Unauthorized) { call, cause ->
            call.respondText("401: You are not authorized to access this resource", status = HttpStatusCode.Unauthorized)
        }

        statusFile(HttpStatusCode.BadRequest, filePattern = "400.html")
    }
}