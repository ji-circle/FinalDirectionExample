package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithArrUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(origin: String, destination: String, arrivalTime: Int) =
        repository.getDirectionsWithArrival(origin, destination, arrivalTime)
}