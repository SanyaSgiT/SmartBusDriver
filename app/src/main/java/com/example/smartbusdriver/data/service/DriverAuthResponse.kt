package com.example.smartbusdriver.data.service

import com.example.smartbusdriver.domain.Token
import com.example.smartbusdriver.domain.Driver
import kotlinx.serialization.Serializable

@Serializable
data class DriverAuthResponse(
    val driver: Driver,
    val token: Token
)
