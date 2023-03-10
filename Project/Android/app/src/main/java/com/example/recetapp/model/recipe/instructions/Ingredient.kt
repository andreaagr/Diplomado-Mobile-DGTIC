package com.example.recetapp.model.recipe.instructions

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Int,
    val name: String,
    @SerializedName("image")
    var imageName: String
)
