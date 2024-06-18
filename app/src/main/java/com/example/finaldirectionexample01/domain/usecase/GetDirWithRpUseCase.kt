package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithRp(origin, destination, transitRoutingPreference)
}