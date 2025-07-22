package com.example.application

import com.example.config.configureResources
import com.example.routing.test.configureTestRouting
import com.example.config.configureSerialization
import com.example.config.configureStatusPages
import com.example.routing.person.configurePersonRouting
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureResources()
    configureTestRouting()
    configurePersonRouting()
    configureSerialization()
//    configureStatusPages()
}
