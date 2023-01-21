package com.andreagr.greatwondersapi

data class GreatWonder(
    val name: String,
    val image: String,
    val description: String,
    val wonderLocation: WonderLocation
)

data class WonderLocation(
    val city: String,
    val country: String,
    val flagImage: String,
    val coords: Coords
)

data class Coords(
    val latitude: Double,
    val longitude: Double
)