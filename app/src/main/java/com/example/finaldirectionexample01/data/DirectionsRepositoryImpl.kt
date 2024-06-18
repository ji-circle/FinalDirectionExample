package com.example.finaldirectionexample01.data

import android.util.Log
import com.example.finaldirectionexample01.data.network.DirectionsApiService
import com.example.finaldirectionexample01.domain.DirectionsEntity
import com.example.finaldirectionexample01.domain.DirectionsRepository

class DirectionsRepositoryImpl(
    private val apiService: DirectionsApiService
) : DirectionsRepository {

    override suspend fun getDirections(
        origin: String,
        destination: String,
        mode: String
    ): DirectionsEntity {
        val result = apiService.getDirections(origin, destination, mode).toEntity()
        Log.d("확인", "impl: $result")
        return result
    }
}