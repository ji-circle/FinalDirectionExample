package com.example.finaldirectionexample01.data.model

data class Leg(
    val distanceMeters: Int,
    val duration: String,
    val endLocation: EndLocation,
    val localizedValues: LocalizedValues,
    val polyline: Polyline,
    val startLocation: StartLocation,
    val staticDuration: String,
    val steps: List<Step>,
    val stepsOverview: StepsOverview
)