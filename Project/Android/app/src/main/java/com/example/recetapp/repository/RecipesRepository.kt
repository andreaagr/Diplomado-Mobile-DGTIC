package com.example.recetapp.repository

import com.example.recetapp.CategoryType
import com.example.recetapp.ui.UIResponseState

interface RecipesRepository {
    suspend fun getRandomRecipes(): UIResponseState
    suspend fun getIngredientImage(id: Int): UIResponseState

    suspend fun getRecipeByIngredients(
        ingredients: String
    ): UIResponseState

    suspend fun getRecipeByCategory(categoryName: String, categoryType: CategoryType): UIResponseState

    suspend fun getRecipeInformation(recipeId: Int): UIResponseState
}