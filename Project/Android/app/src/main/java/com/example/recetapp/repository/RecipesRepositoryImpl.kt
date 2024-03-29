package com.example.recetapp.repository

import com.example.recetapp.data.RecipeDao
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.view.CarouselRecipe
import com.example.recetapp.networking.RemoteApi
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.util.CategoryType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteApi,
    private val recipeDao: RecipeDao
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

    override fun saveRecipeToFavorites(recipe: Recipe) {
        recipeDao.addSavedRecipe(recipe)
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getSavedRecipes()
    }

    override fun removeRecipeFromFavorites(recipe: Recipe) {
        return recipeDao.deleteSavedRecipe(recipe)
    }

    override fun getCarouselRecipes(): Flow<List<CarouselRecipe>> {
        return recipeDao.getCarouselRecipes()
    }

    override fun addNewCarouselRecipe(carouselRecipe: CarouselRecipe) {
         recipeDao.addNewCarouselRecipe(carouselRecipe)
    }

    override fun deleteAllCarouselRecipes() {
        recipeDao.deleteAllCarouselRecipes()
    }

    override suspend fun searchRecipes(query: String): UIResponseState {
        return remoteDataSource.searchRecipes(query)
    }
}