package com.example.finaldirectionexample01.domain

interface DirectionsRepository {
    suspend fun getDirections(origin: String, destination: String, mode: String): DirectionsEntity
}