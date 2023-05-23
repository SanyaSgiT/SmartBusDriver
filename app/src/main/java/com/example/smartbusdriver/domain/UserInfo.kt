package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    val id: Int,
    val login: String,
    val name: String,
)