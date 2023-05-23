package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    var id: Int,
    val route: String,
    val name: String,
    val transportType: TransportType,
    val firstStop: FirstStop,
    val lastStop: LastStop
)
