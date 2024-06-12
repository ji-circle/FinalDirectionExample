package com.example.finaldirectionexample01.data

import android.util.Log
import com.example.finaldirectionexample01.data.model.DirectionsResponse
import com.example.finaldirectionexample01.data.network.DirectionsApiService
import com.example.finaldirectionexample01.domain.DirectionsEntity

class DirectionsRepositoryImpl(
    private val apiService: DirectionsApiService
) : DirectionsRepository {

    override suspend fun getDirections(
        origin: String,
        destination: String,
        mode: String
    ): DirectionsEntity {
//        val apiKey = "AIzaSyBqe8TQyjF1ndxlzGoZ6GYiWokc8Mi-77U"
        val result = apiService.getDirections(origin, destination, mode).toEntity()
        Log.d("확인", "impl: $result")
        return result
    }
}