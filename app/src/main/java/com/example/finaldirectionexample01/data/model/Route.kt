package com.example.finaldirectionexample01.data.model

data class Route(
    val distanceMeters: Int,
    val duration: String,
    val legs: List<Leg>,
    val localizedValues: LocalizedValuesXXX,
    val polyline: PolylineXX,
    val routeLabels: List<String>,
    val staticDuration: String,
    val travelAdvisory: TravelAdvisory,
    val viewport: Viewport
)