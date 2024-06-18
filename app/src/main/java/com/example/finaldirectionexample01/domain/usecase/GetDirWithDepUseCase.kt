package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithDepUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(origin: String, destination: String, departureTime: Int) =
        repository.getDirectionsWithDeparture(origin, destination, departureTime)
}