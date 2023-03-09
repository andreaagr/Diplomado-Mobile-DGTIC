package com.example.recetapp.model.recipe.instructions

import com.google.gson.annotations.SerializedName

data class Equipment(
    val name: String,
    @SerializedName("image")
    val imageName: String
)
