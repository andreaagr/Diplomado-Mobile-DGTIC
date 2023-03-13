package com.example.recetapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.view.CarouselRecipe

@Database(entities = [Recipe::class, CarouselRecipe::class], version = 1)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
}