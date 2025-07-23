package com.example.application

import com.example.config.configureRequestValidation
import com.example.config.configureResources
import com.example.config.configureSerialization
import com.example.config.configureStatusPages
import com.example.routing.person.configurePersonRouting
import com.example.routing.test.configureTestRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureStatusPages()
    configureResources()
    configureTestRouting()
    configurePersonRouting()
    configureSerialization()
    configureRequestValidation()
}
