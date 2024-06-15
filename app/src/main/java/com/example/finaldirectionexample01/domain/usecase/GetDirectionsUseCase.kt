package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository

class GetDirectionsUseCase
constructor(private val repository: DirectionsRepository){
    suspend operator fun invoke(origin: String, destination: String, mode: String) = repository.getDirections(origin, destination, mode)
}