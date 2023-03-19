package com.example.recetapp.ui.recipedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.FragmentRecipeDetailsBinding
import com.example.recetapp.ui.recipedetails.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!
    private val navArgs by navArgs<RecipeDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRecipeDetailsBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecipe()
        with(binding) {
            viewPager.adapter = activity?.let { FragmentAdapter(it, navArgs.displayRecipe) }
            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = when(position) {
                    0 -> "Summary"
                    1 -> "Ingredients"
                    2 -> "Instructions"
                    else -> ""
                }
            }.attach()
        }

    }

    private fun showRecipe() {
        val recipe =  navArgs.displayRecipe
        with(binding) {
            recipeTitleTextView.text = recipe.title
            readyInMinutesTextView.text = recipe.timeOfPreparation.toString() + " min"
            pricePerServingTextView.text= "$" + recipe.pricePerServing
            servingTextView.text = "Serves " +recipe.servings
            Glide.with(root)
                .load(recipe.imageUrl)
                .centerCrop()
                .into(recipeImageView)
        }
    }
}