package com.example.recetapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetapp.R
import com.example.recetapp.databinding.CarouselItemBinding
import com.example.recetapp.model.recipe.Recipe

typealias OnDiscoverRecipeSelected = (Recipe) -> Unit

class CarouselRVAdapter(
    private val carouselDataList: List<Recipe>,
    private val onDiscoverRecipeSelected: OnDiscoverRecipeSelected
) : RecyclerView.Adapter<CarouselItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        return  LayoutInflater.from(parent.context)
            .let { layoutInflater -> CarouselItemBinding.inflate(layoutInflater, parent, false)  }
            .let { itemBinding -> CarouselItemViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.bind(carouselDataList[position], onDiscoverRecipeSelected)
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}

class CarouselItemViewHolder(
    private val binding: CarouselItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recipe: Recipe,
        onDiscoverRecipeSelected: OnDiscoverRecipeSelected
    ) {
        with(binding) {
            Glide.with(root)
                .load(recipe.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.recipe_placeholder)
                .into(carouselRecipeImageView)
            carouselTitleTextView.text = recipe.title
            carouselItemContainer.setOnClickListener { onDiscoverRecipeSelected(recipe) }
        }
    }
}