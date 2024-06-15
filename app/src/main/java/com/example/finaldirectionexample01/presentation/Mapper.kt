package com.example.finaldirectionexample01.presentation

import com.example.finaldirectionexample01.data.model.Bounds
import com.example.finaldirectionexample01.data.model.DirectionsGeocodedWaypoint
import com.example.finaldirectionexample01.data.model.DirectionsLeg
import com.example.finaldirectionexample01.data.model.DirectionsPolyline
import com.example.finaldirectionexample01.data.model.DirectionsResponse
import com.example.finaldirectionexample01.data.model.DirectionsRoute
import com.example.finaldirectionexample01.data.model.DirectionsStep
import com.example.finaldirectionexample01.data.model.DirectionsTrafficSpeedEntry
import com.example.finaldirectionexample01.data.model.DirectionsTransitAgency
import com.example.finaldirectionexample01.data.model.DirectionsTransitDetails
import com.example.finaldirectionexample01.data.model.DirectionsTransitLine
import com.example.finaldirectionexample01.data.model.DirectionsTransitStop
import com.example.finaldirectionexample01.data.model.DirectionsTransitVehicle
import com.example.finaldirectionexample01.data.model.DirectionsViaWaypoint
import com.example.finaldirectionexample01.data.model.Fare
import com.example.finaldirectionexample01.data.model.TextValueObject
import com.example.finaldirectionexample01.data.model.TimeZoneTextValueObject
import com.example.finaldirectionexample01.data.toEntity
import com.example.finaldirectionexample01.domain.BoundsEntity
import com.example.finaldirectionexample01.domain.DirectionsEntity
import com.example.finaldirectionexample01.domain.DirectionsGeocodedWaypointEntity
import com.example.finaldirectionexample01.domain.DirectionsLegEntity
import com.example.finaldirectionexample01.domain.DirectionsPolylineEntity
import com.example.finaldirectionexample01.domain.DirectionsRouteEntity
import com.example.finaldirectionexample01.domain.DirectionsStepEntity
import com.example.finaldirectionexample01.domain.DirectionsTrafficSpeedEntryEntity
import com.example.finaldirectionexample01.domain.DirectionsTransitAgencyEntity
import com.example.finaldirectionexample01.domain.DirectionsTransitDetailsEntity
import com.example.finaldirectionexample01.domain.DirectionsTransitLineEntity
import com.example.finaldirectionexample01.domain.DirectionsTransitStopEntity
import com.example.finaldirectionexample01.domain.DirectionsTransitVehicleEntity
import com.example.finaldirectionexample01.domain.DirectionsViaWaypointEntity
import com.example.finaldirectionexample01.domain.FareEntity
import com.example.finaldirectionexample01.domain.LatLngEntity
import com.example.finaldirectionexample01.domain.TextValueObjectEntity
import com.example.finaldirectionexample01.domain.TimeZoneTextValueObjectEntity
import com.google.android.gms.maps.model.LatLng

fun DirectionsEntity.toModel() = DirectionsModel(
    routes = routes.map {
        it.toModel()
    }.orEmpty(),
    directionsStatus = directionsStatus ?: "",
    availableTravelModes = availableTravelModes.orEmpty(),
    geocodedWaypoints = geocodedWaypoints.map {
        it.toModel()
    }.orEmpty()
)

fun DirectionsGeocodedWaypointEntity.toModel() = DirectionsGeocodedWaypointModel(
    geocoderStatus = geocoderStatus ?: "",
    partialMatch = partialMatch ?: false,
    placeId = placeId ?: "",
    types = types.orEmpty()
)

fun DirectionsRouteEntity.toModel() = DirectionsRouteModel(
    bounds = bounds.toModel() ?: BoundsModel(
        LatLngModel(0.0, 0.0), LatLngModel(0.0, 0.0)
    ),
    copyrights = copyrights ?: "",
    legs = legs.map {
        it.toModel()
    }.orEmpty(),
    overviewPolyline = overviewPolyline.toModel() ?: DirectionsPolylineModel(points = ""),
    summary = summary ?: "",
    warnings = warnings ?: emptyList(),
    waypointOrder = waypointOrder ?: emptyList(),
    fare = fare.toModel() ?: FareModel(currency = "", text = "", value = 0.0)

)

fun BoundsEntity.toModel() = BoundsModel(
    northeast = northeast.toModel() ?: LatLngModel(0.0, 0.0),
    southwest = southwest.toModel()?: LatLngModel(0.0, 0.0)
)

fun LatLngEntity.toModel() = LatLngModel(
    lat = lat ?: 0.0,
    lng = lng ?: 0.0
)

fun DirectionsLegEntity.toModel() = DirectionsLegModel(
    totalEndAddress = totalEndAddress ?: "",
    totalEndLocation = totalEndLocation.toModel(),
    totalStartAddress = totalStartAddress ?: "",
    totalStartLocation = totalStartLocation.toModel(),
    steps = steps.map {
        it.toModel()
    }.orEmpty(),
    trafficSpeedEntry = trafficSpeedEntry.map {
        it.toModel()
    }.orEmpty(),  // 수정된 부분
    viaWaypoint = viaWaypoint.map {
        it.toModel()
    }.orEmpty(),
    totalArrivalTime = totalArrivalTime.toModel() ?: TimeZoneTextValueObjectModel(
        text = "",
        timeZone = "",
        value = 0.0
    ), // 수정된 부분
    totalDepartureTime = totalDepartureTime.toModel() ?: TimeZoneTextValueObjectModel(
        text = "",
        timeZone = "",
        value = 0.0
    ), // 수정된 부분
    totalDistance = totalDistance.toModel() ?: TextValueObjectModel(text = "", value = 0.0),
    totalDuration = totalDuration.toModel() ?: TextValueObjectModel(text = "", value = 0.0),
    durationInTraffic = durationInTraffic.toModel() ?: TextValueObjectModel(
        text = "",
        value = 0.0
    )
)


fun DirectionsStepEntity.toModel(): DirectionsStepModel {
    return DirectionsStepModel(
        stepDuration = stepDuration.toModel() ?: TextValueObjectModel(text = "", value = 0.0),
        endLocation = endLocation.toModel()?: LatLngModel(0.0, 0.0),
        htmlInstructions = htmlInstructions ?: "",
        polyline = polyline.toModel() ?: DirectionsPolylineModel(points = ""),
        startLocation = startLocation.toModel()?: LatLngModel(0.0, 0.0),
        travelMode = travelMode ?: "",
        distance = distance.toModel() ?: TextValueObjectModel(text = "", value = 0.0),
        stepInSteps = stepInSteps.map { it.toModel() } ?: emptyList(),
        transitDetails = transitDetails.toModel() ?: DirectionsTransitDetailsModel(
            arrivalStop = DirectionsTransitStopModel(
                location = LatLngModel(0.0, 0.0),
                name = ""
            ),
            arrivalTime = TimeZoneTextValueObjectModel(text = "", timeZone = "", value = 0.0),
            departureStop = DirectionsTransitStopModel(
                location = LatLngModel(0.0, 0.0),
                name = ""
            ),
            departureTime = TimeZoneTextValueObjectModel(text = "", timeZone = "", value = 0.0),
            headSign = "",
            headWay = 0,
            line = DirectionsTransitLineModel(
                agencies = emptyList(),
                name = "",
                color = "",
                icon = "",
                shortName = "",
                textColor = "",
                url = "",
                vehicle = DirectionsTransitVehicleModel(
                    name = "",
                    type = "",
                    icon = "",
                    localIcon = ""
                )
            ),
            numStops = 0,
            tripShortName = ""
        )
    )
}


fun DirectionsTransitDetailsEntity.toModel() = DirectionsTransitDetailsModel(
    arrivalStop = arrivalStop.toModel() ?: DirectionsTransitStopModel(
        location = LatLngModel(0.0, 0.0),
        name = ""
    ),
    arrivalTime = arrivalTime.toModel() ?: TimeZoneTextValueObjectModel(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    departureStop = departureStop.toModel() ?: DirectionsTransitStopModel(
        location = LatLngModel(0.0, 0.0),
        name = ""
    ),
    departureTime = departureTime.toModel() ?: TimeZoneTextValueObjectModel(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    headSign = headSign ?: "",
    headWay = headWay ?: 0,
    line = line.toModel() ?: DirectionsTransitLineModel(
        agencies = emptyList(),
        color = "",
        icon = "",
        name = "",
        shortName = "",
        textColor = "",
        url = "",
        vehicle = DirectionsTransitVehicleModel(name = "", type = "", icon = "", localIcon = "")
    ),
    numStops = numStops ?: 0,
    tripShortName = tripShortName ?: ""
)

fun DirectionsPolylineEntity.toModel() = DirectionsPolylineModel(
//  points = points ?: ""
    points
)

fun DirectionsTransitStopEntity.toModel() = DirectionsTransitStopModel(
//    location = location?.toModel() ?: LatLngModel(0.0, 0.0),
//    name = name ?: ""
    location = location.toModel(), name
    //location = LatLngModel(0.0,0.0), name = "
)

fun DirectionsTransitLineEntity.toModel() = DirectionsTransitLineModel(
//    agencies = agencies?.map { it.toModel() } ?: emptyList(),
//    name = name ?: "",
//    color = color ?: "",
//    icon = icon ?: "",
//    shortName = shortName ?: "",
//    textColor = textColor ?: "",
//    url = url ?: "",
//    vehicle = vehicle?.toModel() ?: DirectionsTransitVehicleModel(
//        name = "",
//        type = "",
//        icon = "",
//        localIcon = ""
//    )
    agencies = agencies.map { it.toModel() } ?: emptyList(),
    name,
    color,
    icon,
    shortName,
    textColor,
    url,
    vehicle = vehicle.toModel() ?: DirectionsTransitVehicleModel(
        name = "", type = "", icon = "", localIcon = ""
    )

)

fun DirectionsTransitAgencyEntity.toModel() = DirectionsTransitAgencyModel(
//    name = name ?: "",
//    phone = phone ?: "",
//    url = url ?: ""
    name, phone, url
)

fun DirectionsTransitVehicleEntity.toModel() = DirectionsTransitVehicleModel(
//    name = name ?: "",
//    type = type ?: "",
//    icon = icon ?: "",
//    localIcon = localIcon ?: ""
    name, type, icon, localIcon
)

fun DirectionsTrafficSpeedEntryEntity.toModel() = DirectionsTrafficSpeedEntryModel(
//    offsetMeters = offsetMeters ?: 0.0,
//    speedCategory = speedCategory ?: ""
    offsetMeters, speedCategory
)

fun DirectionsViaWaypointEntity.toModel() = DirectionsViaWaypointModel(
//    location = location.toModel() ?: LatLngModel(0.0, 0.0),
//    stepIndex = stepIndex ?: 0,
//    stepInterpolation = stepInterpolation ?: 0
//
//    location = location.toModel(), stepIndex, stepInterpolation
    location= location.toModel(), stepIndex, stepInterpolation
)

fun TimeZoneTextValueObjectEntity.toModel() = TimeZoneTextValueObjectModel(
    text, timeZone, value
)

fun TextValueObjectEntity.toModel() = TextValueObjectModel(
    text, value
)

fun FareEntity.toModel() = FareModel(
    currency, text, value
)

