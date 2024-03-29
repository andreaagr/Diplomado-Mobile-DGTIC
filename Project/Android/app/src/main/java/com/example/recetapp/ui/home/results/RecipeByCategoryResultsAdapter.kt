package com.example.recetapp.ui.home.results


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.RecipeCardItemGeneralBinding
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.util.ScreenResultType
import com.example.recetapp.util.formatFromHTML
import com.google.android.material.chip.Chip

typealias OnFavoriteTapped = (Recipe) -> Unit
typealias OnUnFavoriteTapped = (Recipe) -> Unit
typealias OnRecipeClicked = (Recipe, Boolean) -> Unit
class RecipeByCategoryResultsAdapter(
    private val onFavoriteTapped: OnFavoriteTapped,
    private val onUnFavoriteTapped: OnUnFavoriteTapped,
    private val onRecipeClicked: OnRecipeClicked,
    private val isFrom: ScreenResultType
): ListAdapter<Recipe, GeneralRecipeViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralRecipeViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> RecipeCardItemGeneralBinding.inflate(layoutInflater, parent, false)  }
            .let { itemBinding -> GeneralRecipeViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: GeneralRecipeViewHolder, position: Int) {
        holder.bind(getItem(position), onFavoriteTapped, onUnFavoriteTapped, onRecipeClicked, isFrom)
    }

}

class GeneralRecipeViewHolder(
    private val binding: RecipeCardItemGeneralBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        recipe: Recipe,
        onFavoriteTapped: OnFavoriteTapped,
        onUnFavoriteTapped: OnUnFavoriteTapped,
        onRecipeClicked: OnRecipeClicked,
        isFrom: ScreenResultType
    ) {
        with(binding) {
            Glide.with(root)
                .load(recipe.imageUrl)
                .circleCrop()
                .into(recipeByCategoryImageView)
            recipeByCategoryTitle.text = recipe.title
            summaryTextView.text = recipe.summary?.let { formatFromHTML(it) }
            recipe.diets?.forEach {
                chipGroup.addView(
                    Chip(root.context).apply {
                        text = it
                    }
                )
            }
            if (isFrom == ScreenResultType.FAVORITES) {
                toggleFavoriteButton.isChecked = true
                //toggleSaveButton.visibility = View.GONE
            } else {
                toggleFavoriteButton.isChecked = recipe.isFavorite
            }
            toggleFavoriteButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onFavoriteTapped(recipe)
                } else {
                    onUnFavoriteTapped(recipe)
                }
            }
            recipeCardView.setOnClickListener { onRecipeClicked(recipe, toggleFavoriteButton.isChecked) }
        }
    }
}