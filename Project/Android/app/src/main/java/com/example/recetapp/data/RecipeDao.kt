package com.example.recetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.view.CarouselRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Insert
    fun addSavedRecipe(recipe: Recipe)

    @Delete
    fun deleteSavedRecipe(recipe: Recipe)

    @Query("SELECT * FROM carousel")
    fun getCarouselRecipes(): Flow<List<CarouselRecipe>>

    @Insert
    fun addNewCarouselRecipe(carouselRecipe: CarouselRecipe)

    @Query("DELETE FROM carousel")
    fun deleteAllCarouselRecipes()
}