package com.example.smartbusdriver.data.api

import com.example.smartbusdriver.data.service.DriverAuthResponse
import com.example.smartbusdriver.data.service.CreateUserRequest
import com.example.smartbusdriver.data.service.LinkToRouteRequest
import com.example.smartbusdriver.data.service.LoginRequest
import com.example.smartbusdriver.domain.Driver
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DriverApi {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int): Driver?

    @POST("account/driver/login")
    suspend fun authentication(@Body loginRequest: LoginRequest): DriverAuthResponse

    @POST("account/driver/register")
    suspend fun createUser(@Body createUserRequest: CreateUserRequest): DriverAuthResponse

    @POST("/driver/route")
    suspend fun linkToRoute(@Body linkToRouteRequest: LinkToRouteRequest)
}