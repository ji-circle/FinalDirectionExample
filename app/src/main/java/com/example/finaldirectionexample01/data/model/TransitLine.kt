package com.example.finaldirectionexample01.data.model

data class TransitLine(
    val agencies: List<Agency>,
    val color: String,
    val name: String,
    val nameShort: String,
    val textColor: String,
    val vehicle: Vehicle
)