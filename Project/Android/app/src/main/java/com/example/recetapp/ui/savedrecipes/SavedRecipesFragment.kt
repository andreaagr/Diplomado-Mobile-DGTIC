package com.example.recetapp.ui.savedrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.ScreenResultType
import com.example.recetapp.databinding.FragmentSavedRecipesBinding
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.ui.UIResponseState
import com.example.recetapp.ui.home.results.RecipeByCategoryResultsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedRecipesFragment : Fragment() {

    private var _binding: FragmentSavedRecipesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SavedRecipeViewModel by viewModels()
    private val favoritesAdapter by lazy {
        RecipeByCategoryResultsAdapter(
            {
                // State not reached
            },
            {
                viewModel.removeFavorite(it)
            },
            ScreenResultType.FAVORITES
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)
        viewModel.onCreateView()
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteRecipesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = favoritesAdapter
        }
    }

    private fun handleUIState(uiResponseState: UIResponseState) {
        when(uiResponseState) {
            is UIResponseState.Success<*> -> {
                if (uiResponseState.content is List<*>) {
                    favoritesAdapter.submitList(uiResponseState.content as List<Recipe>)
                }
            }
            is UIResponseState.Error -> {
                // Show error interface
                Toast.makeText(activity, uiResponseState.errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}