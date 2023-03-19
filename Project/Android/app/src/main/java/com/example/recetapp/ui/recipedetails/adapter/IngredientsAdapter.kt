package com.example.recetapp.ui.recipedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recetapp.Endpoint
import com.example.recetapp.ImageSize
import com.example.recetapp.R
import com.example.recetapp.databinding.IngredientRowBinding
import com.example.recetapp.getImageUrl
import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient

class IngredientsAdapter: ListAdapter<ExtendedIngredient, ExtendedIngredientViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtendedIngredientViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> IngredientRowBinding.inflate(layoutInflater, parent, false) }
            .let { itemBinding -> ExtendedIngredientViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: ExtendedIngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExtendedIngredientViewHolder(
    private val binding: IngredientRowBinding
) : ViewHolder(binding.root) {

    fun  bind(ingredient: ExtendedIngredient) {
        with(binding) {
            ingredientDetailsTextView.text = ingredient.original
            Glide.with(binding.root)
                .load(ingredient.image?.let { getImageUrl(it, Endpoint.INGREDIENT, ImageSize.MEDIUM) })
                .centerCrop()
                .placeholder(R.drawable.ingredient)
                .into(ingredientDetailsImageView)
        }
    }
}