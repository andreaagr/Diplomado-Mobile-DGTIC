package com.andreagr.greatwondersapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GreatWonder(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val location: WonderLocation
): Parcelable

@Parcelize
data class WonderLocation(
    val city: String,
    val country: String,
    val flagImage: String,
    val coords: Coords
): Parcelable

@Parcelize
data class Coords(
    val latitude: Double,
    val longitude: Double
): Parcelable