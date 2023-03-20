package com.example.recetapp.ui.dashboard.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.recetapp.util.Endpoint
import com.example.recetapp.util.ImageSize
import com.example.recetapp.databinding.IngredientListItemBinding
import com.example.recetapp.util.getImageUrl
import com.example.recetapp.model.recipe.instructions.Ingredient

typealias OnDeleteElementClicked = (Int) -> Unit

class AddedIngredientAdapter(
    private val onDeleteElementClicked: OnDeleteElementClicked
): ListAdapter<Ingredient, ImageIngredientViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageIngredientViewHolder {
        return  LayoutInflater.from(parent.context)
            .let { layoutInflater -> IngredientListItemBinding.inflate(layoutInflater, parent, false)  }
            .let { itemBinding -> ImageIngredientViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: ImageIngredientViewHolder, position: Int) {
        holder.bind(position, getItem(position), onDeleteElementClicked)
    }
}

class ImageIngredientViewHolder(
    private val binding: IngredientListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(
        position: Int,
        ingredient: Ingredient,
        onDeleteElementClicked: OnDeleteElementClicked
    ) {
        with(binding) {
            ingredientNameTextView.text = ingredient.name
            loadAddedIngredientImage(ingredient)
            lottieAnimationSetup(
                deleteButtonAnimated,
                onDeleteElementClicked,
                position
            )
        }
    }

    private fun lottieAnimationSetup(
        lottieAnimationView: LottieAnimationView,
        onDeleteElementClicked: OnDeleteElementClicked,
        position: Int
    ) {
        lottieAnimationView.setOnClickListener {
            lottieAnimationView.playAnimation()
        }

        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                onDeleteElementClicked(position)
                lottieAnimationView.progress = 0F
            }

            override fun onAnimationCancel(p0: Animator) {}

            override fun onAnimationRepeat(p0: Animator) {}
        })
    }

    private fun loadAddedIngredientImage(ingredient: Ingredient) {
        if (ingredient.imageName.isNotEmpty()) {
            Glide.with(binding.root)
                .load(getImageUrl(ingredient.imageName, Endpoint.INGREDIENT, ImageSize.SMALL))
                .fitCenter()
                .into(binding.ingredientImageView)
        }
    }
}