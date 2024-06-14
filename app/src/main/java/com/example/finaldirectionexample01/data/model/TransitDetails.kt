package com.example.finaldirectionexample01.data.model

data class TransitDetails(
    val headsign: String,
    val localizedValues: LocalizedValuesXX,
    val stopCount: Int,
    val stopDetails: StopDetails,
    val transitLine: TransitLine
)