package com.example.finaldirectionexample01.api

import com.example.finaldirectionexample01.data.network.DirectionsApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.Gson


object NetworkClient {

    const val API_KEY = "AIzaSyBqe8TQyjF1ndxlzGoZ6GYiWokc8Mi-77U"

    private val okHttpClient by lazy {
        createOkHttpClient()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val directionsApiService: DirectionsApiService =
        retrofit.create(DirectionsApiService::class.java)
}
