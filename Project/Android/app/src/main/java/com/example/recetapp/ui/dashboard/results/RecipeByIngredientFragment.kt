package com.example.recetapp.ui.dashboard.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.databinding.FragmentRecipeByIngredientBinding
import com.example.recetapp.model.recipe.RecipeByIngredients
import com.example.recetapp.util.SpacingDecoration
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeByIngredientFragment : Fragment() {

    private var _binding: FragmentRecipeByIngredientBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()
    private val resultsAdapter by lazy {
        RecipeByIngredientsResultsAdapter(
            {
                viewModel.addFavorite(it)
            },
            {
                viewModel.removeFavorite(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        viewModel.performActionState.observe(viewLifecycleOwner) {
            val toastMessage: String = when (it) {
                is UIResponseState.Success<*> -> it.content as String
                is UIResponseState.Error -> it.errorMessage
                else -> "Performing action..."
            }
            Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment
        _binding = FragmentRecipeByIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recipesByIngredientRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = resultsAdapter
            addItemDecoration(SpacingDecoration(8))
        }
        resultsAdapter.submitList(listOf())
        viewModel.getRecipesByIngredients()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleUIState(uiState: UIResponseState) {
        when (uiState) {
            is UIResponseState.Loading -> {
                binding.lottieLoadingAnimationView.visibility = View.VISIBLE
            }
            is UIResponseState.Success<*> -> {
                binding.lottieLoadingAnimationView.visibility = View.GONE
                resultsAdapter.submitList(uiState.content as MutableList<RecipeByIngredients>?)
            }
            else -> {
                binding.lottieLoadingAnimationView.visibility = View.GONE
                binding.lottieErrorAnimationView.visibility = View.VISIBLE
            }
        }
    }
}