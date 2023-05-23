package com.example.smartbusdriver.domain

import com.example.smartbusdriver.domain.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val userInfo: UserInfo,
    val notificationToken: String,
    val route: Route? = null
)