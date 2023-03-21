package com.example.recetapp.networking

import com.example.recetapp.model.recipe.toCarouselRecipe
import com.example.recetapp.util.CategoryType
import javax.inject.Inject

class RemoteApi @Inject constructor(
    private val foodService: FoodService
) {
    suspend fun getRandomRecipes() = try {
        val response = foodService.getRandomRecipes(15)
        UIResponseState.Success(
            response.recipes.map { it.toCarouselRecipe() }
        )
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

    suspend fun searchRecipes(query: String) = try {
        UIResponseState.Success(foodService.searchRecipes(query))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "No data found")
    }
}