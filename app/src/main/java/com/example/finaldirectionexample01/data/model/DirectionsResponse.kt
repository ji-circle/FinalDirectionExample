package com.example.finaldirectionexample01.data.model

import com.google.gson.annotations.SerializedName


data class DirectionsResponse(
    @SerializedName("routes") val routes: List<DirectionsRoute>?,
    @SerializedName("status") val directionsStatus: String?,
    @SerializedName("available_travel_modes") val availableTravelModes: List<String>?,
    @SerializedName("geocoded_waypoints") val geocodedWaypoints: List<DirectionsGeocodedWaypoint>?
)

data class DirectionsGeocodedWaypoint(
    @SerializedName("geocoder_status") val geocoderStatus: String?,
    @SerializedName("partial_match") val partialMatch: Boolean?,
    @SerializedName("place_id") val placeId: String?,
    @SerializedName("types") val types: List<String>?
)

data class DirectionsRoute(
    @SerializedName("bounds") val bounds: Bounds?,
    @SerializedName("copyrights") val copyrights: String?,
    @SerializedName("legs") val legs: List<DirectionsLeg>?,
    @SerializedName("overview_polyline") val overviewPolyline: DirectionsPolyline?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("warnings") val warnings: List<String>?,
    @SerializedName("waypoint_order") val waypointOrder: List<Int>?,
    @SerializedName("fare") val fare: Fare?

)

data class Bounds(
    @SerializedName("northeast") val northeast: LatLngLiteral?,
    @SerializedName("southwest") val southwest: LatLngLiteral?
)

data class LatLngLiteral(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double

)

data class DirectionsLeg(
    @SerializedName("end_address") val totalEndAddress: String?,
    @SerializedName("end_location") val totalEndLocation: LatLngLiteral?,
    @SerializedName("start_address") val totalStartAddress: String?,
    @SerializedName("start_location") val totalStartLocation: LatLngLiteral?,
    @SerializedName("steps") val steps: List<DirectionsStep>?,
    @SerializedName("traffic_speed_entry") val trafficSpeedEntry: List<DirectionsTrafficSpeedEntry>?,
    @SerializedName("via_waypoint") val viaWaypoint: List<DirectionsViaWaypoint>?,
    @SerializedName("arrival_time") val totalArrivalTime: TimeZoneTextValueObject?,
    @SerializedName("departure_time") val totalDepartureTime: TimeZoneTextValueObject?,
    @SerializedName("distance") val totalDistance: TextValueObject?,
    @SerializedName("duration") val totalDuration: TextValueObject?,
    @SerializedName("duration_in_traffic") val durationInTraffic: TextValueObject?
)

data class DirectionsStep(
    @SerializedName("duration") val stepDuration: TextValueObject?,
    @SerializedName("end_location") val endLocation:LatLngLiteral?,
    @SerializedName("html_instructions") val htmlInstructions: String?,
    @SerializedName("polyline") val polyline: DirectionsPolyline?,
    @SerializedName("start_location") val startLocation: LatLngLiteral?,
    @SerializedName("travel_mode") val travelMode: String?,
    @SerializedName("distance") val distance: TextValueObject?,
    @SerializedName("steps") val stepInSteps: List<DirectionsStep>?,//
    @SerializedName("transit_details") val transitDetails: DirectionsTransitDetails?
)


data class DirectionsTransitDetails(
    @SerializedName("arrival_stop") val arrivalStop: DirectionsTransitStop?,
    @SerializedName("arrival_time") val arrivalTime: TimeZoneTextValueObject?,
    @SerializedName("departure_stop") val departureStop: DirectionsTransitStop?,
    @SerializedName("departure_time") val departureTime: TimeZoneTextValueObject?,
    @SerializedName("headsign") val headSign: String?,
    @SerializedName("headway") val headWay: Int?,
    @SerializedName("line") val line: DirectionsTransitLine?,
    @SerializedName("num_stops") val numStops: Int?,
    @SerializedName("trip_short_name") val tripShortName: String?
)

data class DirectionsPolyline(
    @SerializedName("points") val points: String?
)

data class DirectionsTransitStop(
    @SerializedName("location") val location: LatLngLiteral?,
    @SerializedName("name") val name: String?
)

data class DirectionsTransitLine(
    @SerializedName("agencies") val agencies: List<DirectionsTransitAgency>?,
    @SerializedName("name") val name: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("short_name") val shortName: String?,
    @SerializedName("text_color") val textColor: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("vehicle") val vehicle: DirectionsTransitVehicle?
)

data class DirectionsTransitAgency(
    @SerializedName("name") val name: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("url") val url: String?
)

data class DirectionsTransitVehicle(
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("local_icon") val localIcon: String?
)

data class DirectionsTrafficSpeedEntry(
    @SerializedName("offset_meters") val offsetMeters: Double?,
    @SerializedName("speed_category") val speedCategory: String?
)

data class DirectionsViaWaypoint(
    @SerializedName("location") val location: LatLngLiteral?,
    @SerializedName("step_index") val stepIndex: Int?,
    @SerializedName("step_interpolation") val stepInterpolation: Double?
)
data class TimeZoneTextValueObject(
    @SerializedName("text") val text: String?,
    @SerializedName("time_zone") val timeZone: String?,
    @SerializedName("value") val value: Double?
)

data class TextValueObject(
    @SerializedName("text") val text: String?,
    @SerializedName("value") val value: Double?
)

data class Fare(
    @SerializedName("currency") val currency: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("value") val value: Double?
)


//{
//    "geocoded_waypoints": [
//        {
//            "geocoder_status": "OK",
//            "partial_match": true,
//            "place_id": "ChIJqxrVY9jraDURTqBi-qe-mAI",
//            "types": [
//                "establishment",
//                "point_of_interest",
//                "train_station",
//                "transit_station"
//            ]
//        },
//        {
//            "geocoder_status": "OK",
//            "partial_match": true,
//            "place_id": "ChIJA3CU42aifDURaq-3csGXvuc",
//            "types": [
//                "establishment",
//                "point_of_interest",
//                "subway_station",
//                "transit_station"
//            ]
//        }
//    ],
//    "routes": [
//        {
//            "bounds": {
//                "northeast": {
//                    "lat": 37.555946,
//                    "lng": 129.138506
//                },
//                "southwest": {
//                    "lat": 35.115225,
//                    "lng": 126.884789
//                }
//            },
//            "copyrights": "Map data ©2024 TMap Mobility",
//            "legs": [
//                {
//                    "arrival_time": {
//                        "text": "오후 11:52",
//                        "time_zone": "Asia/Seoul",
//                        "value": 1718117533
//                    },
//                    "departure_time": {
//                        "text": "오후 9:00",
//                        "time_zone": "Asia/Seoul",
//                        "value": 1718107200
//                    },
//                    "distance": {
//                        "text": "374 km",
//                        "value": 374158
//                    },
//                    "duration": {
//                        "text": "2시간 52분",
//                        "value": 10333
//                    },
//                    "end_address": "대한민국 서울특별시 중구 소공동 세종대로18길 2 서울역",
//                    "end_location": {
//                        "lat": 37.555946,
//                        "lng": 126.972317
//                    },
//                    "start_address": "대한민국 부산광역시 동구 초량제3동 중앙대로 부산",
//                    "start_location": {
//                        "lat": 35.115225,
//                        "lng": 129.042243
//                    },
//                    "steps": [
//                        {
//                            "distance": {
//                                "text": "108 km",
//                                "value": 108040
//                            },
//                            "duration": {
//                                "text": "49분",
//                                "value": 2940
//                            },
//                            "end_location": {
//                                "lat": 35.879667,
//                                "lng": 128.628476
//                            },
//                            "html_instructions": "기차 행신행행",
//                            "polyline": {
//                                "points": "cmiuE_qrrWietAuxQob_AtrbB"
//                            },
//                            "start_location": {
//                                "lat": 35.115225,
//                                "lng": 129.042243
//                            },
//                            "transit_details": {
//                                "arrival_stop": {
//                                    "location": {
//                                        "lat": 35.879667,
//                                        "lng": 128.628476
//                                    },
//                                    "name": "동대구"
//                                },
//                                "arrival_time": {
//                                    "text": "오후 9:49",
//                                    "time_zone": "Asia/Seoul",
//                                    "value": 1718110140
//                                },
//                                "departure_stop": {
//                                    "location": {
//                                        "lat": 35.115225,
//                                        "lng": 129.042243
//                                    },
//                                    "name": "부산"
//                                },
//                                "departure_time": {
//                                    "text": "오후 9:00",
//                                    "time_zone": "Asia/Seoul",
//                                    "value": 1718107200
//                                },
//                                "headsign": "행신행",
//                                "line": {
//                                    "agencies": [
//                                        {
//                                            "name": "korail",
//                                            "url": "https://ga-g.onntapi.com/#1"
//                                        }
//                                    ],
//                                    "name": "KTX 경부",
//                                    "vehicle": {
//                                        "icon": "//maps.gstatic.com/mapfiles/transit/iw2/6/rail2.png",
//                                        "name": "기차",
//                                        "type": "HEAVY_RAIL"
//                                    }
//                                },
//                                "num_stops": 2,
//                                "trip_short_name": "KTX 제166열차"
//                            },
//                            "travel_mode": "TRANSIT"
//                        },
//                        {
//                            "distance": {
//                                "text": "266 km",
//                                "value": 265926
//                            },
//                            "duration": {
//                                "text": "1시간 48분",
//                                "value": 6480
//                            },
//                            "end_location": {
//                                "lat": 37.5547125,
//                                "lng": 126.9707878
//                            },
//                            "html_instructions": "기차 서울행행",
//                            "polyline": {
//                                "points": "}v~yE_wapWclwAdghFmhw@tzSwmzCdmuAu~YoxO"
//                            },
//                            "start_location": {
//                                "lat": 35.879667,
//                                "lng": 128.628476
//                            },
//                            "transit_details": {
//                                "arrival_stop": {
//                                    "location": {
//                                        "lat": 37.5547125,
//                                        "lng": 126.9707878
//                                    },
//                                    "name": "서울역"
//                                },
//                                "arrival_time": {
//                                    "text": "오후 11:49",
//                                    "time_zone": "Asia/Seoul",
//                                    "value": 1718117340
//                                },
//                                "departure_stop": {
//                                    "location": {
//                                        "lat": 35.879667,
//                                        "lng": 128.628476
//                                    },
//                                    "name": "동대구"
//                                },
//                                "departure_time": {
//                                    "text": "오후 10:01",
//                                    "time_zone": "Asia/Seoul",
//                                    "value": 1718110860
//                                },
//                                "headsign": "서울행",
//                                "line": {
//                                    "agencies": [
//                                        {
//                                            "name": "korail",
//                                            "url": "https://ga-g.onntapi.com/#1"
//                                        }
//                                    ],
//                                    "name": "KTX 경부",
//                                    "vehicle": {
//                                        "icon": "//maps.gstatic.com/mapfiles/transit/iw2/6/rail2.png",
//                                        "name": "기차",
//                                        "type": "HEAVY_RAIL"
//                                    }
//                                },
//                                "num_stops": 4,
//                                "trip_short_name": "KTX 제262열차"
//                            },
//                            "travel_mode": "TRANSIT"
//                        },
//                        {
//                            "distance": {
//                                "text": "0.2 km",
//                                "value": 192
//                            },
//                            "duration": {
//                                "text": "3분",
//                                "value": 193
//                            },
//                            "end_location": {
//                                "lat": 37.555946,
//                                "lng": 126.972317
//                            },
//                            "html_instructions": "대한민국 서울특별시 중구 소공동 세종대로18길 2 서울역까지 도보",
//                            "polyline": {
//                                "points": "}{edFm~}eWwFqH"
//                            },
//                            "start_location": {
//                                "lat": 37.5547125,
//                                "lng": 126.9707878
//                            },
//                            "steps": [
//                                {
//                                    "distance": {
//                                        "text": "0.2 km",
//                                        "value": 192
//                                    },
//                                    "duration": {
//                                        "text": "3분",
//                                        "value": 193
//                                    },
//                                    "end_location": {
//                                        "lat": 37.555946,
//                                        "lng": 126.972317
//                                    },
//                                    "polyline": {
//                                        "points": "}{edFm~}eWwFqH"
//                                    },
//                                    "start_location": {
//                                        "lat": 37.5547125,
//                                        "lng": 126.9707878
//                                    },
//                                    "travel_mode": "WALKING"
//                                }
//                            ],
//                            "travel_mode": "WALKING"
//                        }
//                    ],
//                    "traffic_speed_entry": [],
//                    "via_waypoint": []
//                }
//            ],
//            "overview_polyline": {
//                "points": "cmiuE_qrrWietAuxQob_AtrbBclwAdghFmhw@tzSwmzCdmuAu~YoxOwFqH"
//            },
//            "summary": "",
//            "warnings": [
//                "도보 경로는 베타 서비스입니다. 주의 – 이 경로에는 인도 또는 보행 경로가 누락되었을 수도 있습니다."
//            ],
//            "waypoint_order": []
//        }
//    ],
//    "status": "OK"
//}