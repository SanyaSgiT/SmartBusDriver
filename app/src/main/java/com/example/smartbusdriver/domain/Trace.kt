package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Trace(
    val id: Int,
    val latLng: LatLng,
    val stop: Stop? = null
)