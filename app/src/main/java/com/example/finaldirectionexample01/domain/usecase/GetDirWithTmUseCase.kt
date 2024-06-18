package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirWithTmUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(origin: String, destination: String, transitMode: String) =
        repository.getDirectionsWithTm(origin, destination, transitMode)
}