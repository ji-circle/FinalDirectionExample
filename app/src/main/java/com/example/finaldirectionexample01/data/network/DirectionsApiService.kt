package com.example.finaldirectionexample01.data.network

import com.example.finaldirectionexample01.data.model.DirectionsResponse
import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DirectionsApiService {

    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String,
        @Query("language") language: String = "ko",
//        @Header("key") apiKey: String = "AIzaSyCAOdeHz6erGcY_sbcEqbEgAETVpirfiV8"
        @Query("key") apiKey: String = "AIzaSyBqe8TQyjF1ndxlzGoZ6GYiWokc8Mi-77U"
    ): DirectionsResponse
}