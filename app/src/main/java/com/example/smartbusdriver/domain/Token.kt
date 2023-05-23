package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val token: String,
    val type: String = "Bearer"
)