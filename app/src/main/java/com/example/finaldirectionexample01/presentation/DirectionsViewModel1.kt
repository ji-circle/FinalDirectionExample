package com.example.finaldirectionexample01.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import kotlinx.coroutines.launch

class DirectionsViewModel1 (private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModel() {
    private val _directionsResult = MutableLiveData<DirectionsModel>()
    val directionsResult: LiveData<DirectionsModel> get() = _directionsResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getDirections(origin: String, destination: String, mode: String) {
        Log.d("확인", "$origin, $destination, $mode")
        viewModelScope.launch {
            try {
                val result = getDirectionsUseCase(origin, destination, mode)
                _directionsResult.postValue(result.toModel())
                Log.d("확인", "viewmodel: ${_directionsResult.value}")
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}

class DirectionsViewModel1Factory(private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectionsViewModel1::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DirectionsViewModel1(getDirectionsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

