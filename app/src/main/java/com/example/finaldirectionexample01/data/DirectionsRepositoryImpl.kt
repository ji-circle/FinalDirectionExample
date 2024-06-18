package com.example.finaldirectionexample01.data

import android.util.Log
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
        val result = apiService.getDirections(origin, destination, mode).toEntity()
        Log.d("확인", "impl: $result")
        return result
    }

    override suspend fun getDirectionsWithDeparture(
        origin: String,
        destination: String,
        departureTime: Int
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithDeparture(origin, destination, departureTime).toEntity()
        Log.d("확인", "impl 2: $result")
        return result
    }

    override suspend fun getDirectionsWithTm(
        origin: String,
        destination: String,
        transitMode: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithTm(origin, destination, transitMode).toEntity()
        Log.d("확인", "impl 3: $result")
        return result
    }

    override suspend fun getDirectionsWithRp(
        origin: String,
        destination: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithRp(origin, destination, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 4: $result")
        return result
    }

    override suspend fun getDirectionsWithArrival(
        origin: String,
        destination: String,
        arrivalTime: Int
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithArrival(origin, destination, arrivalTime).toEntity()
        Log.d("확인", "impl 5: $result")
        return result
    }

    override suspend fun getDirectionsWithDepartureTm(
        origin: String,
        destination: String,
        departureTime: Int,
        transitMode: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithDepartureTm(origin, destination, departureTime, transitMode).toEntity()
        Log.d("확인", "impl 6: $result")
        return result
    }

    override suspend fun getDirectionsWithDepartureRp(
        origin: String,
        destination: String,
        departureTime: Int,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithDepartureRp(origin, destination, departureTime, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 7: $result")
        return result
    }

    override suspend fun getDirectionsWithDepartureTmRp(
        origin: String,
        destination: String,
        departureTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithDepartureTmRp(origin, destination, departureTime, transitMode, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 8: $result")
        return result
    }

    override suspend fun getDirectionsWithTmRp(
        origin: String,
        destination: String,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithTmRp(origin, destination, transitMode, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 9: $result")
        return result
    }

    override suspend fun getDirectionsWithArrivalRp(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithArrivalRp(origin, destination, arrivalTime, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 10: $result")
        return result
    }

    override suspend fun getDirectionsWithArrivalTm(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitMode: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithArrivalTm(origin, destination, arrivalTime, transitMode).toEntity()
        Log.d("확인", "impl 11: $result")
        return result
    }

    override suspend fun getDirectionsWithArrivalTmRp(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsWithArrivalTmRp(origin, destination, arrivalTime, transitMode, transitRoutingPreference).toEntity()
        Log.d("확인", "impl 12: $result")
        return result
    }

}