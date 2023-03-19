package com.example.recetapp.model.recipe.instructions

import android.os.Parcelable
import com.example.recetapp.Endpoint
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: Int,
    override val name: String,
    @SerializedName("image")
    override var imageName: String,
): Parcelable, Tool
