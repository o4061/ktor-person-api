package com.example.config

import com.example.routing.person.PersonBody
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {

        validate<PersonBody> { body ->
            when {
                body.name.isBlank() -> ValidationResult.Invalid("Name cannot be blank")
                body.age < 0 -> ValidationResult.Invalid("Age must be greater than or equal to 0")
                body.money <= 0 -> ValidationResult.Invalid("Money must be greater than 0")
                else -> ValidationResult.Valid
            }
        }

        validate<List<PersonBody>> { body ->
            val firstInvalid = body.firstOrNull { person ->
                person.name.isBlank() || person.age < 0 || person.money <= 0
            }

            if (firstInvalid != null) {
                ValidationResult.Invalid("Invalid person: $firstInvalid")
            } else {
                ValidationResult.Valid
            }
        }
    }
}