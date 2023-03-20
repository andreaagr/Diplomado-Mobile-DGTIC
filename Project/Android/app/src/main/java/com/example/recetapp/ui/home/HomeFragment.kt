package com.example.recetapp.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.work.*
import com.example.recetapp.CategoryType
import com.example.recetapp.R
import com.example.recetapp.databinding.FragmentHomeBinding
import com.example.recetapp.model.category.Category
import com.example.recetapp.model.view.CarouselRecipe
import com.example.recetapp.model.view.CategorySelected
import com.example.recetapp.model.view.toRecipe
import com.example.recetapp.ui.UIResponseState
import com.example.recetapp.ui.home.adapter.CarouselRVAdapter
import com.example.recetapp.ui.home.adapter.CategoriesAdapter
import com.example.recetapp.work.SynchronizeDataWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        viewModel.loadRecipes()
        synchronizeApi(ExistingPeriodicWorkPolicy.KEEP)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerSetup()
        categoriesRVSetup(binding.cuisinesRecyclerView, R.raw.cuisines, CategoryType.CUISINE)
        categoriesRVSetup(binding.mealTypesRecyclerView, R.raw.meal_types, CategoryType.MEAL_TYPE)
        categoriesRVSetup(binding.dietsRecyclerView, R.raw.diets, CategoryType.DIET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleUIState(uiState: UIResponseState?) {
        when(uiState) {
            is UIResponseState.Success<*> -> {
                if (uiState.content is List<*>) {
                    // Random recipes call
                    if (uiState.content.isNotEmpty()) {
                        binding.lottieErrorAnimationView.visibility = View.GONE
                        binding.carouselViewPager.adapter = CarouselRVAdapter(uiState.content as List<CarouselRecipe>) { carouselRecipe ->
                            val recipe = carouselRecipe.toRecipe()
                            recipe.isFavorite = viewModel.isFavorite(recipe)
                            HomeFragmentDirections.actionNavigationHomeToRecipeDetailsFragment(recipe).let {
                                findNavController().navigate(it)
                            }
                        }
                    } else {
                        binding.lottieErrorAnimationView.visibility = View.VISIBLE
                        //synchronizeApi(ExistingPeriodicWorkPolicy.UPDATE)
                    }
                }
            }
            is UIResponseState.Error -> {
                binding.lottieErrorAnimationView.visibility = View.VISIBLE
            }
            else -> {}
        }
    }

    private fun viewPagerSetup() {
        /* Adding space between elements of the ViewPager */
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.carouselViewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
            setPageTransformer(compositePageTransformer)
        }
    }

    private fun categoriesRVSetup(
        recyclerView: RecyclerView,
        json: Int,
        type: CategoryType
    ) {
        val categoriesAdapter = CategoriesAdapter { category ->
            viewModel.selectCategory(
                CategorySelected(
                    category.name,
                    type
                )
            )
            HomeFragmentDirections
                .actionNavigationHomeToRecipesByCategoriesFragment()
                .let { destination -> findNavController().navigate(destination) }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = categoriesAdapter
        }

        val categories = loadCategoriesFromJSON(json)
        categoriesAdapter.submitList(categories)
    }

    private fun loadCategoriesFromJSON(res: Int): List<Category> {
        val categories = resources.openRawResource(res).bufferedReader().use { it.readText() }
        val typeToken = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(categories, typeToken)
    }

    private fun synchronizeApi(workPolicy: ExistingPeriodicWorkPolicy){
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val work = PeriodicWorkRequestBuilder<SynchronizeDataWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        val workManager = activity?.baseContext?.let { WorkManager.getInstance(it) }
        workManager?.enqueueUniquePeriodicWork("sync", workPolicy, work)
        workManager?.getWorkInfoByIdLiveData(work.id)?.observe(viewLifecycleOwner) { workInfo: WorkInfo? ->
            if (workInfo != null) {
                when (workInfo.progress.getInt("Progress", 100)) {
                    0 -> binding.lottieLoadingAnimationView.visibility = View.VISIBLE
                    100 -> {
                        binding.lottieLoadingAnimationView.visibility = View.GONE
                        viewModel.loadRecipes()
                    }
                }
            }
        }
    }
}