package com.example.smartbusdriver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Stop(
    val id: Int,
    val name: String,
    val latLng: LatLng,
    val len: Int
)