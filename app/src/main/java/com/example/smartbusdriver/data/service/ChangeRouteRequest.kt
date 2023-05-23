package com.example.smartbusdriver.data.service

import kotlinx.serialization.Serializable

@Serializable
data class ChangeRouteRequest(val routeId: Int?)
