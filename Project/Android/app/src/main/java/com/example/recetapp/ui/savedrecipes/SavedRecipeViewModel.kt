package com.example.recetapp.ui.savedrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.networking.UIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipeViewModel @Inject constructor(
    private val repository: RecipesRepository
): ViewModel() {
    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()

    fun onCreateView() {
        viewModelScope.launch {
            _viewState.postValue(UIResponseState.Loading)
            refreshFavorites("Failed to collect items")
        }
    }

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeRecipeFromFavorites(recipe)
            refreshFavorites("Something went wrong")
        }
    }

    private suspend fun refreshFavorites(errorMessage: String) {
        repository
            .getFavoriteRecipes()
            .flowOn(Dispatchers.IO)
            .catch {
                _viewState.postValue(UIResponseState.Error(errorMessage))
            }
            .collect {
                _viewState.postValue(UIResponseState.Success(it))
            }
    }
}