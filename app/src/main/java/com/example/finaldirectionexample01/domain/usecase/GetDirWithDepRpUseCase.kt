package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithDepRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        departureTime: Int,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithDepartureRp(
        origin,
        destination,
        departureTime,
        transitRoutingPreference
    )
}