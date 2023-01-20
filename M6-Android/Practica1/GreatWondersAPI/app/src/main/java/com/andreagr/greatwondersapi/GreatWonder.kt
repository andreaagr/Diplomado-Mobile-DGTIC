package com.andreagr.greatwondersapi

data class GreatWonder(
    val name: String,
    val image: String,
    val city: String,
    val country: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)