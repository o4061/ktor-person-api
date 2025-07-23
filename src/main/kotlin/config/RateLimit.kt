package com.example.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ratelimit.RateLimit
import io.ktor.server.plugins.ratelimit.RateLimitName
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(RateLimitName("public")) {
            rateLimiter(limit = 5, refillPeriod = 60.seconds)
        }

        register(RateLimitName("protected")) {
            rateLimiter(limit = 40, refillPeriod = 60.seconds)
        }
    }
}