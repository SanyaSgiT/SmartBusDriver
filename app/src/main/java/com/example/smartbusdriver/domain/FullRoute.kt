package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class FullRoute(
    val route: Route,
    val traces: List<Trace>
)