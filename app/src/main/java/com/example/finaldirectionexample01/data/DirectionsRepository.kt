package com.example.finaldirectionexample01.data

import android.text.Layout
import com.example.finaldirectionexample01.data.model.DirectionsResponse
import com.example.finaldirectionexample01.data.network.DirectionsApiService
import com.example.finaldirectionexample01.domain.DirectionsEntity

//class DirectionsRepository(private val apiService: DirectionsApiService) {
//    suspend fun getDirections(origin: String, destination: String, mode: String): DirectionsEntity {
//        val apiKey = "AIzaSyBqe8TQyjF1ndxlzGoZ6GYiWokc8Mi-77U"
//        val response = apiService.getDirections(origin, destination, mode, apiKey)
//        return response.toEntity()
//    }
//}

interface DirectionsRepository {
    suspend fun getDirections(origin: String, destination: String, mode: String): DirectionsEntity
}