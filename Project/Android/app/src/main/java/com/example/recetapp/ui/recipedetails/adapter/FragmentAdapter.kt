package com.example.recetapp.ui.recipedetails.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.ui.recipedetails.RecipeIngredientsFragment
import com.example.recetapp.ui.recipedetails.RecipeInstructionsFragment
import com.example.recetapp.ui.recipedetails.RecipeSummaryFragment

class FragmentAdapter(
    activity: FragmentActivity,
    private val recipe: Recipe
) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecipeSummaryFragment.newInstance(recipe.summary ?: "An unexpected error occurred")
            1 -> RecipeIngredientsFragment.newInstance(recipe.extendedIngredients ?: listOf())
            else -> RecipeInstructionsFragment.newInstance(recipe.analyzedInstructions ?: listOf())
        }
    }
}