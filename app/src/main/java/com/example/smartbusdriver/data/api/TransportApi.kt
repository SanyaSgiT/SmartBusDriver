package com.example.smartbusdriver.data.api

import com.example.smartbusdriver.data.service.RoutesResponse
import com.example.smartbusdriver.data.service.TracesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransportApi {
    @GET("routes")
    suspend fun getAllRoutes(): RoutesResponse

    @GET("routes/{id}")
    suspend fun findRouteByName(@Query("id") id: Int): RoutesResponse

    @GET("routes/{id}")
    suspend fun getTrace(@Path("id") id: Int): TracesResponse
}