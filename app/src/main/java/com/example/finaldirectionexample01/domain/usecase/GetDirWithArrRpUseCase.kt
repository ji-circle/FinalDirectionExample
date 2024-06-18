package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithArrRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithArrivalRp(
        origin,
        destination,
        arrivalTime,
        transitRoutingPreference
    )
}