package com.example.recetapp.model.view

import android.media.Image
import com.google.gson.annotations.SerializedName

data class BasicUIElement(
    val name: String,
    @SerializedName("image")
    val imageUrl: Image
)

data class IngredientItem(
    val name: String,
    val id: Int
)