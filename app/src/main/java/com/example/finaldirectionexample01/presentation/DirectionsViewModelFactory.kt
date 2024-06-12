package com.example.finaldirectionexample01.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase

//class DirectionsViewModelFactory(private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DirectionsViewModel::class.java)) {
//            return DirectionsViewModel(getDirectionsUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}