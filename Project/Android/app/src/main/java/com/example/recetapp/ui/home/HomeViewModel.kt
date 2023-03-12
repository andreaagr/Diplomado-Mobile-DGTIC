package com.example.recetapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetapp.model.view.CategorySelected
import com.example.recetapp.model.response.ComplexSearchResponse
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.ui.UIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipesRepository
): ViewModel() {

    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()
    val viewStateResults: LiveData<UIResponseState> get() = _viewStateResults
    private val _viewStateResults: MutableLiveData<UIResponseState> = MutableLiveData()
    val categorySelected: LiveData<CategorySelected> get() = _categorySelected
    private val _categorySelected: MutableLiveData<CategorySelected> = MutableLiveData()

    fun onCreate() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(repository.getRandomRecipes())
        }
    }

    fun selectCategory(category: CategorySelected) {
        _categorySelected.postValue(category)
    }

    fun searchByCategory(category: CategorySelected) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewStateResults.postValue(UIResponseState.Loading)
            val response = repository.getRecipeByCategory(category.name, category.type)
            if (response is UIResponseState.Success<*>) {
                val newRecipeList: MutableList<Recipe> = mutableListOf()
                if (response.content is ComplexSearchResponse) {
                    response.content.results.forEach {
                        val recipeInformationResponse = repository.getRecipeInformation(it.id)
                        if (recipeInformationResponse is UIResponseState.Success<*>) {
                            if (recipeInformationResponse.content is Recipe)
                                newRecipeList.add(recipeInformationResponse.content)
                        }
                    }
                }
                _viewStateResults.postValue(UIResponseState.Success(newRecipeList))
            } else {
                UIResponseState.Error("Something went wrong")
            }
        }
    }

    fun addFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) { repository.saveRecipeToFavorites(recipe) }
    }

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch { repository.removeRecipeFromFavorites(recipe) }
    }
}
