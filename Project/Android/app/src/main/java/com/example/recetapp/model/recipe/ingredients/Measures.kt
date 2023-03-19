package com.example.recetapp.model.recipe.ingredients

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Measures(
    val metric: Metric?,
    val us: Us?
): Parcelable

@Parcelize
data class Metric(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
): Parcelable

@Parcelize
data class Us(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
): Parcelable