package com.example.finaldirectionexample01.data

import com.example.finaldirectionexample01.domain.DirectionsEntity

interface DirectionsRepository {
    suspend fun getDirections(origin: String, destination: String, mode: String): DirectionsEntity

    suspend fun getDirectionsWithDeparture(origin:String, destination: String, departureTime: Int): DirectionsEntity

    suspend fun getDirectionsWithTm(origin:String, destination:String, transitMode: String): DirectionsEntity

    suspend fun getDirectionsWithRp(origin: String, destination: String, transitRoutingPreference:String):DirectionsEntity

    suspend fun getDirectionsWithArrival(origin: String, destination: String, arrivalTime: Int): DirectionsEntity

    suspend fun getDirectionsWithDepartureTm(origin: String, destination: String, departureTime: Int, transitMode: String): DirectionsEntity

    suspend fun getDirectionsWithDepartureRp(origin: String, destination: String, departureTime: Int, transitRoutingPreference: String): DirectionsEntity

    suspend fun getDirectionsWithDepartureTmRp(origin: String, destination: String, departureTime: Int, transitMode: String, transitRoutingPreference: String): DirectionsEntity

    suspend fun getDirectionsWithTmRp(origin: String, destination: String, transitMode: String, transitRoutingPreference: String): DirectionsEntity

    suspend fun getDirectionsWithArrivalRp(origin: String, destination: String, arrivalTime: Int, transitRoutingPreference: String): DirectionsEntity

    suspend fun getDirectionsWithArrivalTm(origin: String, destination: String, arrivalTime: Int, transitMode: String): DirectionsEntity

    suspend fun getDirectionsWithArrivalTmRp(origin: String, destination: String, arrivalTime: Int, transitMode: String, transitRoutingPreference: String): DirectionsEntity
}