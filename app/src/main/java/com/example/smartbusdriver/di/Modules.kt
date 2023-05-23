package com.example.smartbusdriver.di

import android.content.Context
import com.example.smartbusdriver.data.api.DriverApi
import com.example.smartbusdriver.data.api.TransportApi
import com.example.smartbusdriver.data.repository.RoutesRepository
import com.example.smartbusdriver.data.storage.UserStorage
import com.example.smartbusdriver.ui.routeList.RouteListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    factory { RoutesRepository(get()) }
    factory { UserStorage(androidApplication().getSharedPreferences("UserStorage", Context.MODE_PRIVATE)) }
}

val presentationModule = module {
    viewModel { RouteListViewModel(get()) }
}

val networkModule = module {
    factory {
        val contentType = "application/json".toMediaTypeOrNull()!!

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(
                Interceptor { chain ->
                    val token = get<UserStorage>().token ?: return@Interceptor chain.proceed(chain.request())

                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", "Bearer $token")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            .build()

        Retrofit.Builder()
            .baseUrl("http://37.194.210.121:4721/")
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    factory { get<Retrofit>().create(DriverApi::class.java) }
    factory { get<Retrofit>().create(TransportApi::class.java) }
}