package com.example.recetapp.networking

import com.example.recetapp.ui.UIResponseState
import javax.inject.Inject

class RemoteApi @Inject constructor(
    private val greatWondersService: FoodService
) {
    suspend fun getRandomRecipes() = try {
        UIResponseState.Success(greatWondersService.getRandomRecipes(1))
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Something went wrong")
    }
}