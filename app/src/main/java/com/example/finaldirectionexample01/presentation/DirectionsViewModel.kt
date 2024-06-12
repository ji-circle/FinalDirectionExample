package com.example.finaldirectionexample01.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData

//class DirectionsViewModel(private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModel() {
//    private val _directionsLiveData = MutableLiveData<List<Route>>()
//    val directionsLiveData: LiveData<List<Route>> = _directionsLiveData
//
//    fun getDirections(origin: String, destination: String, mode: String) {
//        viewModelScope.launch {
//            val response = getDirectionsUseCase(origin, destination, mode)
//            _directionsLiveData.postValue(response.routes)
//        }
//    }
//}