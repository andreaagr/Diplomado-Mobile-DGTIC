package com.example.recetapp.networking

import com.example.recetapp.ui.UIResponseState
import javax.inject.Inject

class RemoteApi @Inject constructor(
    private val foodService: FoodService
) {
    suspend fun getRandomRecipes() = try {
        UIResponseState.Success(foodService.getRandomRecipes(1))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Something went wrong")
    }

    suspend fun getIngredientImage(id: Int) = try {
        UIResponseState.Success(foodService.getIngredientImage(id))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "")
    }

    suspend fun getRecipeByIngredients(ingredients: String): UIResponseState = try {
        UIResponseState.Success(foodService.getRecipeByIngredients(ingredients))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Failed to retrieve items")
    }
}