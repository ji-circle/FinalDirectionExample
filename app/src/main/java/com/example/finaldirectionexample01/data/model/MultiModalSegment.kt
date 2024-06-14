package com.example.finaldirectionexample01.data.model

data class MultiModalSegment(
    val navigationInstruction: NavigationInstructionX,
    val stepEndIndex: Int,
    val stepStartIndex: Int,
    val travelMode: String
)