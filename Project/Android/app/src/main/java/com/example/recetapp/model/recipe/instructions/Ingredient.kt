package com.example.recetapp.model.recipe.instructions

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val name: String,
    @SerializedName("image")
    val imageName: String
)
