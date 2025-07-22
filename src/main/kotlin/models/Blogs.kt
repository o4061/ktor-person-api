package com.example.models

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/blogs/{id}")
data class Blogs(
    val parent: Account = Account(),
    val id: String,
    val q1: String? = null,
    val q2: String? = null,
)