package com.andreagr.semana4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(
    val id: Int,
    val image: String,
    val name: String,
    val weight: Float,
    val sex: String,
    val description: String
): Parcelable
