package com.example.smartbusdriver.data.mappers

import com.example.smartbusdriver.data.service.RouteResponse
import com.example.smartbusdriver.domain.Route

fun RouteResponse.asRoute() = Route(id, route, name, transportType, firstStop, lastStop)