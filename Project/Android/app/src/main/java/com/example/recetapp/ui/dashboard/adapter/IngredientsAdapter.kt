package com.example.recetapp.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recetapp.databinding.SimpleListItemBinding
import com.example.recetapp.model.view.IngredientItem

typealias OnElementClicked = (IngredientItem) -> Unit

class IngredientsAdapter(
    private val onElementClicked: OnElementClicked
): ListAdapter<IngredientItem, IngredientViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IngredientItem>() {
            override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return  LayoutInflater.from(parent.context)
            .let { layoutInflater -> SimpleListItemBinding.inflate(layoutInflater, parent, false)  }
            .let { itemBinding -> IngredientViewHolder(itemBinding, onElementClicked) }
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class IngredientViewHolder(
    private val binding: SimpleListItemBinding,
    private val onElementClicked: OnElementClicked
): RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: IngredientItem) {
        with(binding) {
            elementTitleTextView.text = ingredient.name
            elementContainer.setOnClickListener { onElementClicked(ingredient) }
        }
    }
}