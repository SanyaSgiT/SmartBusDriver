package com.example.smartbusdriver.data.service

import com.example.smartbusdriver.domain.Route
import kotlinx.serialization.Serializable

@Serializable
data class RoutesResponse(
    val routes: List<Route>
)