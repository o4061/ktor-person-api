package com.example.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.digest

fun Application.configDigestAuthentication() {
    install(Authentication) {
        digest("digest-auth") {

        }
    }
}