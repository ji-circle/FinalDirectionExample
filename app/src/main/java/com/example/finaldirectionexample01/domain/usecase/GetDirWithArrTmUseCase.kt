package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithArrTmUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitMode: String
    ) = repository.getDirectionsWithArrivalTm(origin, destination, arrivalTime, transitMode)
}