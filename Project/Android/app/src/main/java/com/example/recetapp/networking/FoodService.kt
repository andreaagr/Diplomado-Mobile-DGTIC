package com.example.recetapp.networking

import com.example.recetapp.model.response.RandomRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") numberOfResults: Int
    ): RandomRecipeResponse
}