package com.example.recetapp.networking

import com.example.recetapp.model.RecipeByIngredients
import com.example.recetapp.model.recipe.instructions.Ingredient
import com.example.recetapp.model.response.RandomRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") numberOfResults: Int
    ): RandomRecipeResponse

    @GET("food/ingredients/{id}/information")
    suspend fun getIngredientImage(
        @Path("id") ingredientId: Int
    ): Ingredient

    @GET("recipes/findByIngredients")
    suspend fun getRecipeByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 1
    ): List<RecipeByIngredients>

}