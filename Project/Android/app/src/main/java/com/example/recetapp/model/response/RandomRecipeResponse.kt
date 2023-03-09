package com.example.recetapp.model.response

import com.example.recetapp.model.recipe.Recipe

data class RandomRecipeResponse(
    val recipes: List<Recipe>
)