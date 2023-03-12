package com.example.recetapp.model.recipe.ingredients

data class ExtendedIngredient(
    val id: Int,
    val name: String,
    val image: String,
    val measures: Measures,
    val original: String,
    val unitLong: String
)