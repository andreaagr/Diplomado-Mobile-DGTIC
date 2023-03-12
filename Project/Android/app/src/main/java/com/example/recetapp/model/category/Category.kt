package com.example.recetapp.model.category

import com.google.gson.annotations.SerializedName

data class Category(
    val name: String,
    @SerializedName("image")
    val imageUrl: String
)
