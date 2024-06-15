package com.example.finaldirectionexample01.data

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
import com.example.finaldirectionexample01.data.model.LatLngLiteral
import com.example.finaldirectionexample01.data.model.TextValueObject
import com.example.finaldirectionexample01.data.model.TimeZoneTextValueObject
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

fun DirectionsResponse.toEntity() = DirectionsEntity(
    routes = routes?.map {
        it.toEntity()
    }.orEmpty(),
    directionsStatus = directionsStatus ?: "",
    availableTravelModes = availableTravelModes.orEmpty(),
    geocodedWaypoints = geocodedWaypoints?.map {
        it.toEntity()
    }.orEmpty()
)

fun DirectionsGeocodedWaypoint.toEntity() = DirectionsGeocodedWaypointEntity(
    geocoderStatus = geocoderStatus ?: "",
    partialMatch = partialMatch ?: false,
    placeId = placeId ?: "",
    types = types.orEmpty()
)

fun DirectionsRoute.toEntity() = DirectionsRouteEntity(
    bounds = bounds?.toEntity() ?: BoundsEntity(
        northeast = LatLngEntity(
            bounds?.northeast?.lat ?: 0.0, bounds?.northeast?.lng ?: 0.0
        ), southwest = LatLngEntity(0.0, 0.0)
    ),
    copyrights = copyrights ?: "",
    legs = legs?.map {
        it.toEntity()
    }.orEmpty(),
    overviewPolyline = overviewPolyline?.toEntity() ?: DirectionsPolylineEntity(points = ""),
    summary = summary ?: "",
    warnings = warnings ?: emptyList(),
    waypointOrder = waypointOrder ?: emptyList(),
    fare = fare?.toEntity() ?: FareEntity(currency = "", text = "", value = 0.0)

)

fun Bounds.toEntity() = BoundsEntity(
    northeast = northeast?.toEntity() ?: LatLngEntity(0.0, 0.0),
    southwest = southwest?.toEntity() ?: LatLngEntity(0.0, 0.0)
)

fun LatLngLiteral.toEntity() = LatLngEntity(
    lat = lat ?: 0.0,
    lng = lng ?: 0.0
)

fun DirectionsLeg.toEntity() = DirectionsLegEntity(
    totalEndAddress = totalEndAddress ?: "",
    totalEndLocation = totalEndLocation?.toEntity() ?: LatLngEntity(0.0, 0.0),
    totalStartAddress = totalStartAddress ?: "",
    totalStartLocation = totalStartLocation?.toEntity() ?: LatLngEntity(
        0.0, 0.0
    ),
    steps = steps?.map {
        it.toEntity()
    }.orEmpty(),
    trafficSpeedEntry = trafficSpeedEntry?.map {
        it.toEntity()
    }.orEmpty(),
    viaWaypoint = viaWaypoint?.map {
        it.toEntity()
    }.orEmpty(),
    totalArrivalTime = totalArrivalTime?.toEntity() ?: TimeZoneTextValueObjectEntity(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    totalDepartureTime = totalDepartureTime?.toEntity() ?: TimeZoneTextValueObjectEntity(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    totalDistance = totalDistance?.toEntity() ?: TextValueObjectEntity(text = "", value = 0.0),
    totalDuration = totalDuration?.toEntity() ?: TextValueObjectEntity(text = "", value = 0.0),
    durationInTraffic = durationInTraffic?.toEntity() ?: TextValueObjectEntity(
        text = "",
        value = 0.0
    )
)

fun DirectionsStep.toEntity(): DirectionsStepEntity {
    return DirectionsStepEntity(
        stepDuration = stepDuration?.toEntity() ?: TextValueObjectEntity(text = "", value = 0.0),
        endLocation = endLocation?.toEntity() ?: LatLngEntity(0.0, 0.0),
        htmlInstructions = htmlInstructions ?: "",
        polyline = polyline?.toEntity() ?: DirectionsPolylineEntity(points = ""),
        startLocation = startLocation?.toEntity() ?: LatLngEntity(0.0, 0.0),
        travelMode = travelMode ?: "",
        distance = distance?.toEntity() ?: TextValueObjectEntity(text = "", value = 0.0),
        stepInSteps = stepInSteps?.map { it.toEntity() } ?: emptyList(),
        transitDetails = transitDetails?.toEntity() ?: DirectionsTransitDetailsEntity(
            arrivalStop = DirectionsTransitStopEntity(
                location = LatLngEntity(0.0, 0.0),
                name = ""
            ),
            arrivalTime = TimeZoneTextValueObjectEntity(text = "", timeZone = "", value = 0.0),
            departureStop = DirectionsTransitStopEntity(
                location = LatLngEntity(0.0, 0.0),
                name = ""
            ),
            departureTime = TimeZoneTextValueObjectEntity(text = "", timeZone = "", value = 0.0),
            headSign = "",
            headWay = 0,
            line = DirectionsTransitLineEntity(
                agencies = emptyList(),
                name = "",
                color = "",
                icon = "",
                shortName = "",
                textColor = "",
                url = "",
                vehicle = DirectionsTransitVehicleEntity(
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


fun DirectionsTransitDetails.toEntity() = DirectionsTransitDetailsEntity(
    arrivalStop = arrivalStop?.toEntity() ?: DirectionsTransitStopEntity(
        location = LatLngEntity(
            0.0,
            0.0
        ), name = ""
    ),
    arrivalTime = arrivalTime?.toEntity() ?: TimeZoneTextValueObjectEntity(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    departureStop = departureStop?.toEntity() ?: DirectionsTransitStopEntity(
        location = LatLngEntity(
            0.0,
            0.0
        ), name = ""
    ),
    departureTime = departureTime?.toEntity() ?: TimeZoneTextValueObjectEntity(
        text = "",
        timeZone = "",
        value = 0.0
    ),
    headSign = headSign ?: "",
    headWay = headWay ?: 0,
    line = line?.toEntity() ?: DirectionsTransitLineEntity(
        agencies = emptyList(),
        color = "",
        icon = "",
        name = "",
        shortName = "",
        textColor = "",
        url = "",
        vehicle = DirectionsTransitVehicleEntity(name = "", type = "", icon = "", localIcon = "")
    ),
    numStops = numStops ?: 0,
    tripShortName = tripShortName ?: ""
)

fun DirectionsPolyline.toEntity() = DirectionsPolylineEntity(
    points = points ?: ""
)

fun DirectionsTransitStop.toEntity() = DirectionsTransitStopEntity(
    location = location?.toEntity() ?: LatLngEntity(0.0, 0.0),
    name = name ?: ""
)

fun DirectionsTransitLine.toEntity() = DirectionsTransitLineEntity(
    agencies = agencies?.map { it.toEntity() } ?: emptyList(),
    name = name ?: "",
    color = color ?: "",
    icon = icon ?: "",
    shortName = shortName ?: "",
    textColor = textColor ?: "",
    url = url ?: "",
    vehicle = vehicle?.toEntity() ?: DirectionsTransitVehicleEntity(
        name = "",
        type = "",
        icon = "",
        localIcon = ""
    )
)

fun DirectionsTransitAgency.toEntity() = DirectionsTransitAgencyEntity(
    name = name ?: "",
    phone = phone ?: "",
    url = url ?: ""
)

fun DirectionsTransitVehicle.toEntity() = DirectionsTransitVehicleEntity(
    name = name ?: "",
    type = type ?: "",
    icon = icon ?: "",
    localIcon = localIcon ?: ""
)

fun DirectionsTrafficSpeedEntry.toEntity() = DirectionsTrafficSpeedEntryEntity(
    offsetMeters = offsetMeters ?: 0.0,
    speedCategory = speedCategory ?: ""
)

fun DirectionsViaWaypoint.toEntity() = DirectionsViaWaypointEntity(
    location = location?.toEntity() ?: LatLngEntity(0.0, 0.0),
    stepIndex = stepIndex ?: 0,
    stepInterpolation = stepInterpolation ?: 0
)

fun TimeZoneTextValueObject.toEntity() = TimeZoneTextValueObjectEntity(
    text = text ?: "",
    timeZone = timeZone ?: "",
    value = value ?: 0.0
)

fun TextValueObject.toEntity() = TextValueObjectEntity(
    text = text ?: "",
    value = value ?: 0.0
)

fun Fare.toEntity() = FareEntity(
    currency = currency ?: "",
    text = text ?: "",
    value = value ?: 0.0
)

