package com.example.recetapp.repository

import com.example.recetapp.ui.UIResponseState

interface RecipesRepository {
    suspend fun getRandomRecipes(): UIResponseState
}