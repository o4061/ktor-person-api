package com.example.extensions

import io.ktor.server.plugins.ratelimit.RateLimitName
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.routing.Route

fun Route.limited(name: String, block: Route.() -> Unit) {
    rateLimit(RateLimitName(name)) { block() }
}