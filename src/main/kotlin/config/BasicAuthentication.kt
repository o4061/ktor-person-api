package com.example.config

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.*

fun Application.configBasicAuth() {
    val hashUserTableAuth = createHashedUserTable()

    install(Authentication) {
        basic("basic-auth") {
            validate { credentials ->
//                val username = credentials.name
//                val password = credentials.password
//
//                if (username == "admin" && password == "password") {
//                    UserIdPrincipal(username)
//                } else null

                hashUserTableAuth.authenticate(credentials)
            }
        }
    }
}

fun createHashedUserTable(): UserHashedTableAuth {
    val digestFunction = getDigestFunction("SHA-256") { "ktor${it.length}" }

    return UserHashedTableAuth(
        digester = digestFunction,
        table = mapOf(
            "admin" to digestFunction("password"),
            "user" to digestFunction("1234")
        )
    )
}