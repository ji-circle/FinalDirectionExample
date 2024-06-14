package com.example.finaldirectionexample01.presentation

import com.google.android.gms.maps.model.LatLng


data class DirectionsModel(
    val routes: List<DirectionsRouteModel>,
    val directionsStatus: String,
    val availableTravelModes: List<String>,
    val geocodedWaypoints: List<DirectionsGeocodedWaypointModel>
)

data class DirectionsGeocodedWaypointModel(
    val geocoderStatus: String,
    val partialMatch: Boolean,
    val placeId: String,
    val types: List<String>
)

data class DirectionsRouteModel(
    val bounds: BoundsModel,
    val copyrights: String,
    val legs: List<DirectionsLegModel>,
    val overviewPolyline: DirectionsPolylineModel,
    val summary: String,
    val warnings: List<String>,
    val waypointOrder: List<Int>,
    val fare: FareModel
)

data class BoundsModel(
    val northeast: LatLng,
    val southwest: LatLng
)

//data class LatLngLiteralModel(
//    val lat: Double,
//    val lng: Double
//)

data class DirectionsLegModel(
    val totalEndAddress: String,
    val totalEndLocation: LatLng,
    val totalStartAddress: String,
    val totalStartLocation: LatLng,
    val steps: List<DirectionsStepModel>,
    val trafficSpeedEntry: List<DirectionsTrafficSpeedEntryModel>,
    val viaWaypoint: List<DirectionsViaWaypointModel>,
    val totalArrivalTime: TimeZoneTextValueObjectModel,
    val totalDepartureTime: TimeZoneTextValueObjectModel,
    val totalDistance: TextValueObjectModel,
    val totalDuration: TextValueObjectModel,
    val durationInTraffic: TextValueObjectModel
)

data class DirectionsStepModel(
    val stepDuration: TextValueObjectModel,
    val endLocation: LatLng,
    val htmlInstructions: String,
    val polyline: DirectionsPolylineModel,
    val startLocation: LatLng,
    val travelMode: String,
    val distance: TextValueObjectModel,
    val stepInSteps: List<DirectionsStepModel>,
    val transitDetails: DirectionsTransitDetailsModel
)

data class DirectionsTransitDetailsModel(
    val arrivalStop: DirectionsTransitStopModel,
    val arrivalTime: TimeZoneTextValueObjectModel,
    val departureStop: DirectionsTransitStopModel,
    val departureTime: TimeZoneTextValueObjectModel,
    val headSign: String,
    val headWay: Int,
    val line: DirectionsTransitLineModel,
    val numStops: Int,
    val tripShortName: String
)

data class DirectionsPolylineModel(
    val points: String
)

data class DirectionsTransitStopModel(
    val location: LatLng,
    val name: String
)

data class DirectionsTransitLineModel(
    val agencies: List<DirectionsTransitAgencyModel>,
    val name: String,
    val color: String,
    val icon: String,
    val shortName: String,
    val textColor: String,
    val url: String,
    val vehicle: DirectionsTransitVehicleModel
)

data class DirectionsTransitAgencyModel(
    val name: String,
    val phone: String,
    val url: String
)

data class DirectionsTransitVehicleModel(
    val name: String,
    val type: String,
    val icon: String,
    val localIcon: String
)

data class DirectionsTrafficSpeedEntryModel(
    val offsetMeters: Double,
    val speedCategory: String
)

data class DirectionsViaWaypointModel(
    val location: LatLng,
    val stepIndex: Int,
    val stepInterpolation: Number
)

data class TimeZoneTextValueObjectModel(
    val text: String,
    val timeZone: String,
    val value: Double
)

data class TextValueObjectModel(
    val text: String,
    val value: Double
)

data class FareModel(
    val currency: String,
    val text: String,
    val value: Double
)





//data class Route(
//    val overviewPolyline: String,
//    val legs: List<Leg>,
//    val summary: String
//)
//
//data class Leg(
//    val startAddress: String,
//    val endAddress: String,
//    val duration: String,
//    val steps: List<Step>
//)
//
//data class Step(
//    val instructions: String,
//    val duration: String,
//    val travelMode: String,
//    val transitDetails: TransitDetails?
//)
//
//data class TransitDetails(
//    val lineName: String,
//    val vehicleName: String
//)