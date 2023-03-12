package com.example.recetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recetapp.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Insert
    fun addSavedRecipe(recipe: Recipe)

    @Delete
    fun deleteSavedRecipe(recipe: Recipe)
}