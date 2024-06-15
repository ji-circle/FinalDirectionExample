package com.example.finaldirectionexample01.data

import com.example.finaldirectionexample01.domain.DirectionsEntity

interface DirectionsRepository {
    suspend fun getDirections(origin: String, destination: String, mode: String): DirectionsEntity
}