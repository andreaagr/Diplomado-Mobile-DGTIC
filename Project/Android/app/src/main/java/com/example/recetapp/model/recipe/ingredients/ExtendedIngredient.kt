package com.example.recetapp.model.recipe.ingredients

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    val id: Int?,
    val name: String?,
    val image: String?,
    val measures: Measures?,
    val original: String?,
    val unitLong: String?
): Parcelable