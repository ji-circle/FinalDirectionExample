package com.example.finaldirectionexample01.data.model

data class StopDetails(
    val arrivalStop: ArrivalStop,
    val arrivalTime: String,
    val departureStop: DepartureStop,
    val departureTime: String
)