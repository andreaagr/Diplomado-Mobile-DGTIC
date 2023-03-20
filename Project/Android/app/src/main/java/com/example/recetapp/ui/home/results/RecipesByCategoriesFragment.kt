package com.example.recetapp.ui.home.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.databinding.FragmentRecipeByCategoriesBinding
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.ui.home.HomeViewModel
import com.example.recetapp.util.ScreenResultType
import com.example.recetapp.util.SpacingDecoration

class RecipesByCategoriesFragment : Fragment() {

    private var _binding: FragmentRecipeByCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val resultsAdapter by lazy {
        RecipeByCategoryResultsAdapter(
            {
                viewModel.addFavorite(it)
            },
            {
                viewModel.removeFavorite(it)
            },
            { recipe, isFavorite ->
                recipe.isFavorite = isFavorite
                RecipesByCategoriesFragmentDirections
                    .actionRecipesByCategoriesFragmentToRecipeDetailsFragment(recipe)
                    .let { findNavController().navigate(it) }
            },
            ScreenResultType.CATEGORIES
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.categorySelected.observe(this) {
            viewModel.searchByCategory(it)
        }
        viewModel.viewStateResults.observe(this) {
            handleUIState(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        return FragmentRecipeByCategoriesBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchByCategoryResultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = resultsAdapter
            addItemDecoration(SpacingDecoration(10))
        }
    }

    private fun handleUIState(uiState: UIResponseState) {
        with(binding) {
            when (uiState) {
                is UIResponseState.Loading -> {
                    searchByCategoryResultsRecyclerView.visibility = View.GONE
                    lottieLoadingAnimationView.visibility = View.VISIBLE
                }
                is UIResponseState.Success<*> -> {
                    searchByCategoryResultsRecyclerView.visibility = View.VISIBLE
                    lottieLoadingAnimationView.visibility = View.GONE
                    resultsAdapter.submitList(uiState.content as List<Recipe>)
                }
                else -> {
                    lottieLoadingAnimationView.visibility = View.GONE
                    lottieErrorAnimationView.visibility = View.VISIBLE
                }
            }
        }
    }
}