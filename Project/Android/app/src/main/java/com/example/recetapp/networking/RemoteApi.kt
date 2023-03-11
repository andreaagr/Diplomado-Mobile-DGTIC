package com.example.recetapp.networking

import com.example.recetapp.CategoryType
import com.example.recetapp.ui.UIResponseState
import javax.inject.Inject

class RemoteApi @Inject constructor(
    private val foodService: FoodService
) {
    suspend fun getRandomRecipes() = try {
        UIResponseState.Success(foodService.getRandomRecipes(4))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Something went wrong")
    }

    suspend fun getIngredientImage(id: Int) = try {
        UIResponseState.Success(foodService.getIngredientImage(id))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "")
    }

    suspend fun getRecipeByIngredients(ingredients: String) = try {
        UIResponseState.Success(foodService.getRecipeByIngredients(ingredients))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Failed to retrieve items")
    }

    suspend fun getRecipeByCategory(category: String, categoryType: CategoryType): UIResponseState {
        return when(categoryType) {
            CategoryType.CUISINE -> {
                try {
                    UIResponseState.Success(foodService.getRecipeByCuisine(category))
                } catch (error: Throwable) {
                    UIResponseState.Error(error.message ?: "Failed to retrieve items")
                }
            }
            CategoryType.MEAL_TYPE -> {
                try {
                    UIResponseState.Success(foodService.getRecipeByMealType(category))
                } catch (error: Throwable) {
                    UIResponseState.Error(error.message ?: "Failed to retrieve items")
                }
            }
            CategoryType.DIET -> {
                try {
                    UIResponseState.Success(foodService.getRecipeByDiet(category))
                } catch (error: Throwable) {
                    UIResponseState.Error(error.message ?: "Failed to retrieve items")
                }
            }
        }
    }

    suspend fun getRecipeInformation(recipeId: Int) = try {
        UIResponseState.Success(foodService.getRecipeInformation(recipeId))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Failed to retrieve items")
    }
}