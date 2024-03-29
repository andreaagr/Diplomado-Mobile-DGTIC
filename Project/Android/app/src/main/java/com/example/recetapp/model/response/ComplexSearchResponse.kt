package com.example.recetapp.model.response

data class ComplexSearchResponse(
    val results: List<BasicRecipeInfo>
)

data class BasicRecipeInfo(
    val id: Int,
    val imageUrl: String,
    val title: String
)