package com.example.recetapp.ui.recipedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.databinding.FragmentRecipeIngredientsBinding
import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient
import com.example.recetapp.util.SpacingDecoration
import com.example.recetapp.ui.recipedetails.adapter.IngredientsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeIngredientsFragment : Fragment() {

    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val gson = Gson()
    private val binding get() = _binding!!
    private val ingredients: List<ExtendedIngredient>
        get() = gson.fromJson(requireArguments().getString(ARG_INGREDIENTS), object : TypeToken<List<ExtendedIngredient>>() {}.type)
            ?: throw IllegalArgumentException("Argument $ARG_INGREDIENTS required")
    private val ingredientsAdapter = IngredientsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRecipeIngredientsBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ingredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ingredientsAdapter
            addItemDecoration(SpacingDecoration(8))
        }
        ingredientsAdapter.submitList(ingredients)
    }

    companion object {
        private const val ARG_INGREDIENTS = "argIngredientList"

        fun newInstance(ingredients: List<ExtendedIngredient>) =
            RecipeIngredientsFragment().apply {
                arguments = bundleOf(
                    ARG_INGREDIENTS to gson.toJson(ingredients)
                )
            }
    }
}