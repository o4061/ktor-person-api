package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val category: String,
    val price: Int,
)
