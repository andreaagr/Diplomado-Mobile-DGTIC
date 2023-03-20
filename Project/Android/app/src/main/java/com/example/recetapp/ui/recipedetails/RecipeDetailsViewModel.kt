package com.example.recetapp.ui.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipesRepository
): ViewModel() {

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeRecipeFromFavorites(recipe)
        }
    }

    fun addFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRecipeToFavorites(recipe)
        }
    }

}