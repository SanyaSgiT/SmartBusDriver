package com.example.smartbusdriver.data.service

import com.example.smartbusdriver.domain.FirstStop
import com.example.smartbusdriver.domain.LastStop
import com.example.smartbusdriver.domain.TransportType

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    val id: Int,
    val route: String,
    val name: String,
    val transportType: TransportType,
    val firstStop: FirstStop,
    val lastStop: LastStop
)