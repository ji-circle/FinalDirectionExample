package com.example.finaldirectionexample01.data

import com.example.finaldirectionexample01.api.NetworkClient
import com.example.finaldirectionexample01.domain.usecase.GetDirWithDepTmRpUseCase
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import com.example.finaldirectionexample01.presentation.DirectionsViewModel1Factory

class AppContainer {

    private val directionsApiService = NetworkClient.directionsApiService

    val directionsRepository : DirectionsRepository by lazy {
        DirectionsRepositoryImpl(directionsApiService)
    }

    val getDirectionsUseCase: GetDirectionsUseCase by lazy {
        GetDirectionsUseCase(directionsRepository)
    }

    val getDirWithDepTmRpUseCase:GetDirWithDepTmRpUseCase by lazy {
        GetDirWithDepTmRpUseCase(directionsRepository)
    }


    val directions1Container : Directions1Container by lazy {
        Directions1Container(getDirectionsUseCase, getDirWithDepTmRpUseCase)
    }

}

class Directions1Container(
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val getDirWithDepTmRpUseCase: GetDirWithDepTmRpUseCase
){
    val directionsViewModel1Factory = DirectionsViewModel1Factory(
        getDirectionsUseCase, getDirWithDepTmRpUseCase
    )
}