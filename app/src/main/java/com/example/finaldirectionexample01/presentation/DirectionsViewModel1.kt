package com.example.finaldirectionexample01.presentation

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.play.integrity.internal.i
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.launch

class DirectionsViewModel1(private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModel() {
    private val _directionsResult = MutableLiveData<DirectionsModel>()
    val directionsResult: LiveData<DirectionsModel> get() = _directionsResult

    //결과가 여러 개 나오고, 그 중 하나 선택하는 경우 로직 추가하기.. 리스트 추가하고 directionResult에 넣는 과정 추가하기

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _origin = MutableLiveData<String>()
    val origin: LiveData<String> get() = _origin

    private val _destination = MutableLiveData<String>()
    val destination: LiveData<String> get() = _destination

    private val _mode = MutableLiveData<String>()
    val mode: LiveData<String> get() = _mode

    private val _polyLine = MutableLiveData<PolylineOptions>()
    val polyLine: LiveData<PolylineOptions> get() = _polyLine

    private val _latLngBounds = MutableLiveData<List<LatLng>>()
    val latLngBounds: LiveData<List<LatLng>> get() = _latLngBounds

    fun getDirections(origin: String, destination: String, mode: String) {
        Log.d("확인", "$origin, $destination, $mode")
        viewModelScope.launch {
            try {
                updateODM(origin, destination, mode)
                val result = getDirectionsUseCase(origin, destination, mode)
                //_directionsResult.postValue(result.toModel())
                _directionsResult.value = result.toModel()
                //updatePolyLine()
                updatePolyLineWithColors()
                updateBounds()
                Log.d("확인", "viewmodel: ${_directionsResult.value}")
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private fun updateBounds() {
        _latLngBounds.postValue(
            _directionsResult.value?.routes?.get(0)?.bounds?.let {
                listOf(
                    it.northeast,
                    it.southwest
                )
            },
        )
    }

    private fun updateODM(origin: String, destination: String, mode: String) {
        _origin.value = origin
        _destination.value = destination
        _mode.value = mode
    }

    //response: Response<DirectionResponses>
    fun updatePolyLine() {
        val shape = _directionsResult.value?.routes?.get(0)?.overviewPolyline?.points
        _polyLine.postValue(
            PolylineOptions()
                .addAll(PolyUtil.decode(shape))
                .width(8f)
                .color(Color.RED)
        )
    }

    fun updatePolyLineWithColors() {
        //val result = ArrayList<List<LatLng>>()
        val path = ArrayList<LatLng>()

        lateinit var coloredLines: PolylineOptions
        lateinit var coloredLine: PolylineOptions
        try {

            val thisResponse = _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.steps
            if (thisResponse != null) {

                coloredLines = PolylineOptions()

                for (i in 0 until thisResponse.size) {
                    coloredLine =
                        PolylineOptions().addAll(PolyUtil.decode(thisResponse[i].polyline.points))
                            .width(10f)
                            .color(hexToColorInt(thisResponse[i].transitDetails.line.color))
                    for (point in coloredLine.points) {
                        coloredLines.add(point)
                    }

                    //coloredLines.add(coloredLine) //여기에서 더해줘야 해.
                }
                _polyLine.postValue(
                    coloredLines
                )

            }
        } catch (e: Exception) {
            _error.postValue(e.message)
        }

    }

    fun hexToColorInt(hexColor: String): Int {
        return Integer.parseInt(hexColor.removePrefix("#"), 16) or Color.GREEN

    }

    //폴리라인을 디코딩하는 함수
    fun decodePolyline(encoded: String, color: String): List<Pair<LatLng, String>> {
        //fun decodePolyline(encoded: String, color: String): List<LatLng> {
        //val poly = ArrayList<LatLng>()

        val pairList = mutableListOf<Pair<LatLng, String>>()
        var lineColor = ""
        //var poly = LatLng(0.0,0.0)
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            //poly.add(latLng)
            lineColor = color
            pairList.add(Pair(latLng, lineColor))
        }
        return pairList
    }

    //    //폴리라인을 디코딩하는 함수
//    fun decodePolyline(): List<LatLngLiteralModel> {
//
//        val poly = mutableListOf<LatLngLiteralModel>()
//
//        _directionsResult.value?.routes?.get(0)?.overviewPolyline?.points?.let { points ->
//            var index = 0
//            val len = points.length
//            var lat = 0
//            var lng = 0
//            while (index < len) {
//                var b: Int
//                var shift = 0
//                var result = 0
//                do {
//                    b = points[index++].code - 63
//                    result = result or (b and 0x1f shl shift)
//                    shift += 5
//                } while (b >= 0x20)
//                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//                lat += dlat
//                shift = 0
//                result = 0
//                do {
//                    b = points[index++].code - 63
//                    result = result or (b and 0x1f shl shift)
//                    shift += 5
//                } while (b >= 0x20)
//                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//                lng += dlng
//                val latLng = LatLngLiteralModel((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
//                poly.add(latLng)
//            }
//
//        }
//        return poly
//    }
    fun getOrigin(): LatLng {
        return _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalStartLocation ?: LatLng(
            0.0,
            0.0
        )
    }

    fun getDestination(): LatLng {
        return _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalEndLocation
            ?: LatLng(0.0, 0.0)
    }
}

class DirectionsViewModel1Factory(private val getDirectionsUseCase: GetDirectionsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectionsViewModel1::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DirectionsViewModel1(getDirectionsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

