package com.example.recetapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.response.ComplexSearchResponse
import com.example.recetapp.model.view.CategorySelected
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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
    private val _favorites: MutableLiveData<List<Recipe>> = MutableLiveData()
    private val observer = { favoriteRecipes: List<Recipe> ->
        if (_viewStateResults.value is UIResponseState.Success<*>) {
                val result = _viewStateResults.value as UIResponseState.Success<*>
                if (result.content is List<*>) {
                    val recipes = result.content as List<Recipe>
                    recipes.forEach {
                        it.isFavorite = false
                    }
                    favoriteRecipes.forEach {
                        val indexOf = recipes.indexOf(it)
                        if (indexOf != -1) {
                            result.content[indexOf].isFavorite = true
                        }
                    }
                }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteRecipes()
                .flowOn(Dispatchers.IO)
                .collect {
                    _favorites.postValue(it)
                }
        }
        _favorites.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        _favorites.removeObserver(observer)
    }

    fun loadRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCarouselRecipes()
                .flowOn(Dispatchers.IO)
                .catch { _viewState.postValue(UIResponseState.Error(it.message.toString())) }
                .collect {
                    _viewState.postValue(UIResponseState.Success(it))
                }
        }

    }

    fun selectCategory(category: CategorySelected) {
        _categorySelected.postValue(category)
    }

    fun searchByCategory(category: CategorySelected) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewStateResults.postValue(UIResponseState.Loading)
            val response = repository.getRecipeByCategory(category.name, category.type)
            handleComplexResponse(response)
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewStateResults.postValue(UIResponseState.Loading)
            val uiResponseState = repository.searchRecipes(query)
            handleComplexResponse(uiResponseState)
        }
    }

    private suspend fun handleComplexResponse(uiResponseState: UIResponseState) {
        if (uiResponseState is UIResponseState.Success<*>) {
            val newRecipeList: MutableList<Recipe> = mutableListOf()
            if (uiResponseState.content is ComplexSearchResponse) {
                uiResponseState.content.results.forEach {
                    val recipeInformationResponse = repository.getRecipeInformation(it.id)
                    if (recipeInformationResponse is UIResponseState.Success<*>) {
                        if (recipeInformationResponse.content is Recipe) {
                            val recipe = recipeInformationResponse.content
                            recipe.isFavorite = isFavorite(recipe)
                            newRecipeList.add(recipeInformationResponse.content)
                        }
                    }
                }
            }
            _viewStateResults.postValue(UIResponseState.Success(newRecipeList))
        } else {
            _viewStateResults.postValue(UIResponseState.Error("Something went wrong"))
        }
    }

    fun addFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) { repository.saveRecipeToFavorites(recipe) }
    }

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) { repository.removeRecipeFromFavorites(recipe) }
    }

    fun isFavorite(recipe: Recipe): Boolean {
        return _favorites.value?.contains(recipe) ?: false
    }
}
