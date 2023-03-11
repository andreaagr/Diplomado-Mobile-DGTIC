package com.example.recetapp.repository

import com.example.recetapp.CategoryType
import com.example.recetapp.networking.RemoteApi
import com.example.recetapp.ui.UIResponseState
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteApi
) : RecipesRepository {

    override suspend fun getRandomRecipes(): UIResponseState {
        return remoteDataSource.getRandomRecipes()
    }

    override suspend fun getIngredientImage(id: Int): UIResponseState {
        return remoteDataSource.getIngredientImage(id)
    }

    override suspend fun getRecipeByIngredients(ingredients: String): UIResponseState {
        return remoteDataSource.getRecipeByIngredients(ingredients)
    }

    override suspend fun getRecipeByCategory(categoryName: String, categoryType: CategoryType): UIResponseState {
        return remoteDataSource.getRecipeByCategory(categoryName, categoryType)
    }

    override suspend fun getRecipeInformation(recipeId: Int): UIResponseState {
        return remoteDataSource.getRecipeInformation(recipeId)
    }
}