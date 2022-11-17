package com.andreagr.semana3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ZooAnimal(
    val id: Int = 0,
    val name: String,
    val image: String
): Parcelable
