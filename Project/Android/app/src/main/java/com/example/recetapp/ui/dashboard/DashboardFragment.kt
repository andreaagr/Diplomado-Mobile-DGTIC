package com.example.recetapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.R
import com.example.recetapp.databinding.FragmentDashboardBinding
import com.example.recetapp.model.view.IngredientItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()
    private val ingredientList by lazy {
        loadIngredientListFromJSON()
    }
    private var filteredIngredientList: List<IngredientItem> = listOf()
    private var ingredientsAdapter = createIngredientsListAdapter()
    private val addedIngredientsAdapter by lazy {
        AddedIngredientAdapter { index ->
            viewModel.deleteAddedIngredient(index)
        }
    }
    private var firstTimeInApp = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        viewModel.recipeIngredients.observe(viewLifecycleOwner) {
            addedIngredientsAdapter.submitList(it)
            performEmptyScreenActions(it.isNullOrEmpty())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientsAdapter.submitList(ingredientList)
        ingredientsRVSetup()
        addedIngredientsRVSetup()
        searchViewSetup()
        with(binding) {
            clearListButton.setOnClickListener {
                viewModel.clearAddedIngredientList()
            }
            searchRecipesButton.setOnClickListener {
                DashboardFragmentDirections
                    .actionNavigationDashboardToRecipeByIngredientFragment()
                    .let {
                        findNavController().navigate(it)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchViewSetup() {
        with(binding) {
            searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.ingredientsRecyclerView.visibility = View.VISIBLE
                    binding.emptyScreenMessageContainer.visibility = View.GONE
                    if (!firstTimeInApp) {
                        binding.welcomeMessageWIMFTextView.text = getString(R.string.start_adding_ingredients)
                    }
                } else {
                    binding.ingredientsRecyclerView.visibility = View.GONE
                    if (viewModel.recipeIngredients.value.isNullOrEmpty())
                        binding.emptyScreenMessageContainer.visibility = View.VISIBLE
                }
            }

            searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
                        filteredIngredientList = ingredientList.filter { it.name.startsWith(newText) }
                        ingredientsAdapter.submitList(ingredientList.filter { it.name.startsWith(newText) })
                    } else {
                        ingredientsAdapter = createIngredientsListAdapter()
                        binding.ingredientsRecyclerView.swapAdapter(ingredientsAdapter, true)
                        ingredientsAdapter.submitList(ingredientList)
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String) = true
            })
        }
    }

    private fun createIngredientsListAdapter() = IngredientsAdapter {
        viewModel.addNewIngredient(it)
    }

    private fun ingredientsRVSetup() {
        binding.ingredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ingredientsAdapter
            addItemDecoration(
                DividerItemDecoration(this.context,
                    DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun addedIngredientsRVSetup() {
        binding.addedIngredientsRecyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = addedIngredientsAdapter
        }
    }

    private fun loadIngredientListFromJSON(): List<IngredientItem> {
        val ingredients =
            resources.openRawResource(R.raw.ingredients).bufferedReader().use { it.readText() }
        val typeToken = object : TypeToken<List<IngredientItem>>() {}.type
        return Gson().fromJson(ingredients, typeToken)
    }

    private fun performEmptyScreenActions(noAddedIngredients: Boolean) {
        with(binding) {
            clearListButton.isEnabled = !noAddedIngredients
            searchRecipesButton.isEnabled = !noAddedIngredients
            if (noAddedIngredients) {
                binding.emptyScreenMessageContainer.visibility = View.VISIBLE
            }
            firstTimeInApp = false
        }
    }
}