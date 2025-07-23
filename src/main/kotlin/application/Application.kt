package com.example.application

import com.example.config.*
import com.example.routing.person.configurePersonRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureResources()
    configureRateLimit()
    configurePersonRouting()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
}
