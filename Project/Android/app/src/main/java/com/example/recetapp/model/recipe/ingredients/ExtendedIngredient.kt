package com.example.recetapp.model.recipe.ingredients

import com.example.recetapp.model.Measures

data class ExtendedIngredient(
    val id: Int,
    val name: String,
    val image: String,
    val measures: Measures,
    val original: String,
)