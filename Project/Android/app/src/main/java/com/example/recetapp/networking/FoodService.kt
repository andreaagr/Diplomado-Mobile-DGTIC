package com.example.recetapp.networking

import com.example.recetapp.model.response.ComplexSearchResponse
import com.example.recetapp.model.recipe.RecipeByIngredients
import com.example.recetapp.model.recipe.Recipe
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
        @Query("number") number: Int = 10
    ): List<RecipeByIngredients>

    @GET("recipes/complexSearch")
    suspend fun getRecipeByCuisine(
        @Query("cuisine") cuisineName: String
    ): ComplexSearchResponse

    @GET("recipes/complexSearch")
    suspend fun getRecipeByDiet(
        @Query("diet") dietName: String
    ): ComplexSearchResponse

    @GET("recipes/complexSearch")
    suspend fun getRecipeByMealType(
        @Query("type") mealType: String
    ): ComplexSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int
    ): Recipe
}