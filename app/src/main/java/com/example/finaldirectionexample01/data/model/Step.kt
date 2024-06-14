package com.example.finaldirectionexample01.data.model

data class Step(
    val distanceMeters: Int,
    val endLocation: EndLocation,
    val localizedValues: LocalizedValuesX,
    val navigationInstruction: NavigationInstruction,
    val polyline: PolylineX,
    val startLocation: StartLocationX,
    val staticDuration: String,
    val transitDetails: TransitDetails,
    val travelMode: String
)