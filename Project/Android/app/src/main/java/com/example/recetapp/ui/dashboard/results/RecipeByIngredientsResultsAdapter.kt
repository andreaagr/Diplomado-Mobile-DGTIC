package com.example.recetapp.ui.dashboard.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.RecipeCardItemBinding
import com.example.recetapp.model.recipe.RecipeByIngredients

typealias OnFavoriteTappedIncompleteInfo = (Int) -> Unit
class RecipeByIngredientsResultsAdapter(
    private val onFavoriteTapped: OnFavoriteTappedIncompleteInfo
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
        holder.bind(getItem(position), onFavoriteTapped)
    }
}

class RecipeByIngredientIngredientViewHolder(
    private val binding: RecipeCardItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recipeByIngredients: RecipeByIngredients,
        onFavoriteTapped: OnFavoriteTappedIncompleteInfo
    ) {
        with(binding) {
            Glide.with(root)
                .load(recipeByIngredients.image)
                .fitCenter()
                .into(recipeByIngredientImageView)
            recipeByIngredientTitle.text = recipeByIngredients.title
            numberOfUsedIngredients.text = recipeByIngredients.usedIngredientCount.toString() + " used"
            numberOfUnusedIngredients.text = recipeByIngredients.unusedIngredientCount.toString() + " unused"
            numberOfMissedIngredients.text = recipeByIngredients.missedIngredientCount.toString() + " missed"
            toggleFavoriteButton.setOnClickListener {
                onFavoriteTapped(recipeByIngredients.id)
            }
        }
    }
}