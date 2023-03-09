package com.example.recetapp.repository

import com.example.recetapp.networking.RemoteApi
import com.example.recetapp.ui.UIResponseState
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteApi
) : RecipesRepository {

    override suspend fun getRandomRecipes(): UIResponseState {
        return remoteDataSource.getRandomRecipes()
    }
}