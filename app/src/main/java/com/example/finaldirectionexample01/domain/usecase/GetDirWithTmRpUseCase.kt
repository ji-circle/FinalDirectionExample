package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithTmRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        transitMode: String,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithTmRp(origin, destination, transitMode, transitRoutingPreference)
}