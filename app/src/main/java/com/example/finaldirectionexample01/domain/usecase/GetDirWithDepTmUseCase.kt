package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithDepTmUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        departureTime: Int,
        transitMode: String
    ) = repository.getDirectionsWithDepartureTm(origin, destination, departureTime, transitMode)
}