package com.example.recetapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.databinding.FragmentRecipeByCategoriesBinding
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.ui.SpacingDecoration
import com.example.recetapp.ui.UIResponseState

class RecipesByCategoriesFragment : Fragment() {

    private var _binding: FragmentRecipeByCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val resultsAdapter by lazy {
        RecipeByCategoryResultsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.categorySelected.observe(viewLifecycleOwner) {
            viewModel.searchByCategory(it)
        }
        viewModel.viewStateResults.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
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
        when (uiState) {
            is UIResponseState.Loading -> {
                binding.lottieLoadingAnimationView.visibility = View.VISIBLE
            }
            is UIResponseState.Success<*> -> {
                binding.lottieLoadingAnimationView.visibility = View.GONE
                resultsAdapter.submitList(uiState.content as List<Recipe>)
            }
            else -> {
                binding.lottieLoadingAnimationView.visibility = View.GONE
                binding.lottieErrorAnimationView.visibility = View.VISIBLE
            }
        }
    }
}