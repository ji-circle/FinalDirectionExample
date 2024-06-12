package com.example.finaldirectionexample01.domain.usecase

import com.example.finaldirectionexample01.data.DirectionsRepository
import com.example.finaldirectionexample01.domain.DirectionsRouteEntity


//class GetDirectionsUseCase(private val repository: DirectionsRepository) {
//    suspend operator fun invoke(origin: String, destination: String, mode: String): List<Route> {
//        val routes = repository.getDirections(origin, destination, mode)
//        return routes.routes
//    }
//}

class GetDirectionsUseCase
constructor(private val repository: DirectionsRepository){
    suspend operator fun invoke(origin: String, destination: String, mode: String) = repository.getDirections(origin, destination, mode)
}