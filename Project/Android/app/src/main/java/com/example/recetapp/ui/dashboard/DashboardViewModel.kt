package com.example.recetapp.ui.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.example.recetapp.model.IngredientItem
import com.example.recetapp.model.RecipeByIngredients
import com.example.recetapp.model.recipe.instructions.Ingredient
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.ui.UIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: RecipesRepository
): ViewModel() {
    val recipeIngredients: LiveData<List<Ingredient>> get() = _recipeIngredients
    private val _recipeIngredients: MutableLiveData<List<Ingredient>> = MutableLiveData(listOf())
    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()

    fun addNewIngredient(ingredientItem: IngredientItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = _recipeIngredients.value?.toMutableList()
            val newIngredient = Ingredient(
                ingredientItem.id,
                ingredientItem.name,
                ""
            )

            val response = repository.getIngredientImage(ingredientItem.id)
            if(response is UIResponseState.Success<*>) {
                if (response.content is Ingredient) {
                    newIngredient.imageName = response.content.imageName
                }
            }

            tempList.let {
                if (it != null) {
                    if (!it.contains(newIngredient)) {
                        tempList?.add(newIngredient)
                        _recipeIngredients.postValue(
                            it
                        )
                    }
                }
            }
        }
    }

    fun deleteAddedIngredient(index: Int) {
        val tempList = _recipeIngredients.value?.toMutableList()
        tempList.let {
            it?.removeAt(index)
            _recipeIngredients.postValue(it)
        }
    }

    fun clearAddedIngredientList() {
        _recipeIngredients.postValue(listOf())
    }

    fun getRecipesByIngredients() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(UIResponseState.Loading)
            _viewState.postValue(
                _recipeIngredients.value?.joinToString(",") { it.name }
                    ?.let { repository.getRecipeByIngredients(it) }
            )
        }
    }
}