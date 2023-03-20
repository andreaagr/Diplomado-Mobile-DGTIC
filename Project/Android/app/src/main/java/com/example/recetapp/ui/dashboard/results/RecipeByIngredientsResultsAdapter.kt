package com.example.recetapp.ui.dashboard.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.RecipeCardItemBinding
import com.example.recetapp.model.recipe.RecipeByIngredients

typealias OnFavoriteSelectedIncompleteInfo = (Int) -> Unit
typealias OnFavoriteUnSelectedIncompleteInfo = (Int) -> Unit

class RecipeByIngredientsResultsAdapter(
    private val onFavoriteSelected: OnFavoriteSelectedIncompleteInfo,
    private val onFavoriteUnSelected: OnFavoriteUnSelectedIncompleteInfo
) : ListAdapter<RecipeByIngredients, RecipeByIngredientIngredientViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipeByIngredients>() {
            override fun areItemsTheSame(oldItem: RecipeByIngredients, newItem: RecipeByIngredients): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RecipeByIngredients, newItem: RecipeByIngredients): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeByIngredientIngredientViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> RecipeCardItemBinding.inflate(layoutInflater, parent, false) }
            .let { itemBinding -> RecipeByIngredientIngredientViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: RecipeByIngredientIngredientViewHolder, position: Int) {
        holder.bind(getItem(position), onFavoriteSelected, onFavoriteUnSelected)
    }
}

class RecipeByIngredientIngredientViewHolder(
    private val binding: RecipeCardItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recipeByIngredients: RecipeByIngredients,
        onFavoriteSelected: OnFavoriteSelectedIncompleteInfo,
        onFavoriteUnSelected: OnFavoriteUnSelectedIncompleteInfo
    ) {
        with(binding) {
            Glide.with(root)
                .load(recipeByIngredients.image)
                .fitCenter()
                .into(recipeByIngredientImageView)
            recipeByIngredientTitle.text = recipeByIngredients.title
            setSummaryBannerInfo(
                recipeByIngredients.usedIngredientCount.toString(),
                recipeByIngredients.unusedIngredientCount.toString(),
                recipeByIngredients.missedIngredientCount.toString()
            )
            toggleFavoriteButton.isChecked = recipeByIngredients.isFavorite
            toggleFavoriteButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onFavoriteSelected(recipeByIngredients.id)
                } else {
                    onFavoriteUnSelected(recipeByIngredients.id)
                }
            }
        }
    }

    private fun setSummaryBannerInfo(
        usedIngredients: String,
        unusedIngredient: String,
        missedIngredient: String
    ) {
        val used = usedIngredients + "used"
        val unused = unusedIngredient + "unused"
        val missed = missedIngredient + "missed"
        with(binding) {
            numberOfUsedIngredients.text = used
            numberOfUnusedIngredients.text = unused
            numberOfMissedIngredients.text = missed
        }
    }
}