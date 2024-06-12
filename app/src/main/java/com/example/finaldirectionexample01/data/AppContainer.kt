package com.example.finaldirectionexample01.data

import com.example.finaldirectionexample01.api.NetworkClient
import com.example.finaldirectionexample01.data.network.DirectionsApiService
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import com.example.finaldirectionexample01.presentation.DirectionsViewModel1Factory
//import com.example.finaldirectionexample01.presentation.DirectionsViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val directionsApiService = NetworkClient.directionsApiService

    val directionsRepository : DirectionsRepository by lazy {
        DirectionsRepositoryImpl(directionsApiService)
    }

    val getDirectionsUseCase: GetDirectionsUseCase by lazy {
        GetDirectionsUseCase(directionsRepository)
    }

//    val directionsContainer: DirectionsContainer?= null

    var directions1Container : Directions1Container?= null
    //val directions1Container: Directions1Container? by lazy {
    //    Directions1Container(getDirectionsUseCase)
    //}
}

//class DirectionsContainer(
//    private val getDirectionsUseCase: GetDirectionsUseCase
//){
//    val directionsViewModelFactory = DirectionsViewModelFactory(
//        getDirectionsUseCase
//    )
//}

class Directions1Container(
    private val getDirectionsUseCase: GetDirectionsUseCase
){
    val directionsViewModel1Factory = DirectionsViewModel1Factory(
        getDirectionsUseCase
    )
}