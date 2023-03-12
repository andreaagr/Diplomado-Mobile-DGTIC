package com.example.recetapp.model.recipe

import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient

data class RecipeByIngredients(
    val id: Int,
    val title: String,
    val image: String,
    val usedIngredientCount: Int,
    val unusedIngredientCount: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<ExtendedIngredient>,
    val unusedIngredients: List<ExtendedIngredient>,
    val usedIngredients: List<ExtendedIngredient>
)