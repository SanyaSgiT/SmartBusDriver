package com.example.smartbusdriver.data.service

import com.example.smartbusdriver.domain.Route
import com.example.smartbusdriver.domain.Trace
import kotlinx.serialization.Serializable

@Serializable
data class TracesResponse(
    val route: Route,
    val traces: List<Trace>
)