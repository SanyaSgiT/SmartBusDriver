package com.example.smartbusdriver.data.repository

import com.example.smartbusdriver.data.api.TransportApi
import com.example.smartbusdriver.data.mappers.asRoute
import com.example.smartbusdriver.domain.Route

class RoutesRepository(private val api: TransportApi) {
    suspend fun getAllRoutes(): List<Route> = api.getAllRoutes().routes
    suspend fun findRouteByName(id: Int) = api.findRouteByName(id).routes
    suspend fun getTrace(id: Int) = api.getTrace(id).traces
}