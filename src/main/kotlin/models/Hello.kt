package com.example.models

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/hello/{name}")
data class Hello(val name: String)