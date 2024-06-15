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

    private val _polyLine = MutableLiveData<List<PolylineOptions>>()
    val polyLine: LiveData<List<PolylineOptions>> get() = _polyLine

    private val _latLngBounds = MutableLiveData<List<LatLngModel>>()
    val latLngBounds: LiveData<List<LatLngModel>> get() = _latLngBounds

    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> get() = _userLocation

    private val _directionExplanations = MutableLiveData<String>()
    val directionExplanations: LiveData<String> get() = _directionExplanations


    fun getDirections(origin: String, destination: String, mode: String) {
        Log.d("확인", "$origin, $destination, $mode")
        viewModelScope.launch {
            try {
                updateODM(origin, destination, mode)
                val result = getDirectionsUseCase(origin, destination, mode)
                _directionsResult.value = result.toModel()
                updatePolyLineWithColors()
                updateBounds()
                setDirectionsResult()
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

    fun setUserLocation(location: LatLng) {
        _userLocation.value = location
    }

    private fun updateODM(origin: String, destination: String, mode: String) {
        _origin.value = origin
        _destination.value = destination
        _mode.value = mode
    }

    fun updatePolyLineWithColors() {

        try {
            val routes = _directionsResult.value?.routes
            val polylines = mutableListOf<PolylineOptions>()

            routes?.forEach { route ->
                route.legs.forEach { leg ->
                    leg.steps.forEach { step ->
                        val decodedPoints = PolyUtil.decode(step.polyline.points ?: "")
                        val color = hexToColorInt(step.transitDetails.line.color)

                        val coloredLine = PolylineOptions()
                            .addAll(decodedPoints)
                            .width(30f)
                            .color(color)

                        polylines.add(coloredLine)
                    }
                }
            }

            _polyLine.postValue(polylines)

        } catch (e: Exception) {
            _error.postValue(e.message)
        }

    }

    fun hexToColorInt(hexColor: String): Int {
        Log.d("확인", "컬러 int ${hexColor.removePrefix("#")}")
        return try {
            Color.parseColor("#${hexColor.removePrefix("#")}")
        } catch (e: java.lang.IllegalArgumentException) {
            Color.GRAY
        }
    }


    fun getOrigin(): LatLng {
        val lat1 =
            _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalStartLocation?.lat ?: 0.0
        val lng1 =
            _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalStartLocation?.lng ?: 0.0
        Log.d(
            "확인",
            "origin : ${_directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalStartLocation?.lat}"
        )
        return LatLng(lat1, lng1)

    }

    fun getDestination(): LatLng {
        val lat1 =
            _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalEndLocation?.lat ?: 0.0
        val lng1 =
            _directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalEndLocation?.lng ?: 0.0
        Log.d(
            "확인",
            "dest : ${_directionsResult.value?.routes?.get(0)?.legs?.get(0)?.totalEndLocation?.lat}"
        )
        return LatLng(lat1, lng1)
    }

    // 사용자 위치를 문자열로 반환하는 메서드 추가
    fun getUserLocationString(delimiter: String = ","): String? {
        val location = _userLocation.value
        return location?.let {
            "${it.latitude}$delimiter${it.longitude}"
        }
    }

    // directionsResult를 설정하는 메서드
    fun setDirectionsResult() {
        if (_directionsResult.value != null) {
            formatDirectionsExplanations(_directionsResult.value!!)
        } else {
            _error.postValue("_direction null")
            Log.d("확인 setDirections", "null")
        }
    }

    // directionsResult를 기반으로 directionExplanations을 설정하는 메서드
    private fun formatDirectionsExplanations(directions: DirectionsModel) {
        val resultText = StringBuilder()

        directions.routes.forEach { route ->
            route.legs.forEach { leg ->
                resultText.append("🗺️목적지까지 ${leg.totalDistance.text},\n")
                resultText.append("앞으로 ${leg.totalDuration.text} 뒤인\n")
                resultText.append("🕐${leg.totalArrivalTime.text}에 도착 예정입니다.\n")
                resultText.append("\n")
                var num = 1
                leg.steps.forEach { step ->
                    resultText.append("🔷${num}:\n")
                    resultText.append("  상세설명: ${step.htmlInstructions}\n")
                    resultText.append("  소요시간: ${step.stepDuration.text}\n")
                    resultText.append("  구간거리: ${step.distance.text}\n")
                    resultText.append("  이동수단: ${step.travelMode}")

                    if (step.transitDetails != DirectionsTransitDetailsModel(
                            DirectionsTransitStopModel(LatLngModel(0.0, 0.0), ""),
                            TimeZoneTextValueObjectModel("", "", 0.0),
                            DirectionsTransitStopModel(LatLngModel(0.0, 0.0), ""),
                            TimeZoneTextValueObjectModel("", "", 0.0),
                            (""),
                            0,
                            DirectionsTransitLineModel(
                                emptyList(),
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                DirectionsTransitVehicleModel("", "", "", "")
                            ),
                            0,
                            ""
                        )
                    ) {
                        resultText.append(" : ${step.transitDetails.line.shortName}, ${step.transitDetails.line.name}\n")
                        resultText.append("    ${step.transitDetails.headSign} 행\n")
                        resultText.append("    탑승 장소: ${step.transitDetails.departureStop.name}\n")
                        resultText.append("    하차 장소: ${step.transitDetails.arrivalStop.name}\n")
                        resultText.append("    ${step.transitDetails.numStops}")
                        resultText.append(categorizeTransportation(step.transitDetails.line.vehicle.type))
                        resultText.append("\n\n")
                    } else {
                        resultText.append("\n\n\n")
                    }

                    num++
                }
            }
        }

        _directionExplanations.value = resultText.toString()
    }

    // 교통 수단을 카테고라이즈하는 메서드
    private fun categorizeTransportation(transportationType: String): String {
        return when (transportationType) {
            "BUS" -> {
                "개 정류장 이동🚍\n"
            }

            "CABLE_CAR" -> {
                " 케이블 카 이용🚟\n"
            }

            "COMMUTER_TRAIN" -> {
                "개 역 이동🚞\n"
            }

            "FERRY" -> {
                " 페리 이용⛴️\n"
            }

            "FUNICULAR" -> {
                " 푸니큘러 이용🚋\n"
            }

            "GONDOLA_LIFT" -> {
                " 곤돌라 리프트 이용🚠\n"
            }

            "HEAVY_RAIL" -> {
                "개 역 이동🛤️\n"
            }

            "HIGH_SPEED_TRAIN" -> {
                "개 역 이동🚄\n"
            }

            "INTERCITY_BUS" -> {
                "개 정류장 이동🚌\n"
            }

            "LONG_DISTANCE_TRAIN" -> {
                "개 역 이동🚂\n"
            }

            "METRO_RAIL" -> {
                "개 역 이동🚇\n"
            }

            "MONORAIL" -> {
                "개 역 이동🚝\n"
            }

            "OTHER" -> {
                " 이동\n"
            }

            "RAIL" -> {
                "개 역 이동🚃\n"
            }

            "SHARE_TAXI" -> {
                " 공유 택시 이용🚖\n"
            }

            "SUBWAY" -> {
                "개 역 이동🚉\n"
            }

            "TRAM" -> {
                "개 역 트램으로 이동🚊\n"
            }

            "TROLLEYBUS" -> {
                "개 정류장 트롤리버스로 이동🚎\n"
            }

            else -> {
                " 이동\n"
            }
        }
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

