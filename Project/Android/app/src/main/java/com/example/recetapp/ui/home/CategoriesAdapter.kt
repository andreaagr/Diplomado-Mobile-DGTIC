package com.example.recetapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.CategoryItemBinding
import com.example.recetapp.model.category.Category

typealias OnCategorySelected = (Category) -> Unit

class CategoriesAdapter(
    private val onCategorySelected: OnCategorySelected
): ListAdapter<Category, CategoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> CategoryItemBinding.inflate(layoutInflater, parent, false)  }
            .let { itemBinding -> CategoryViewHolder(itemBinding) }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onCategorySelected)
    }
}

class CategoryViewHolder(
    private val binding: CategoryItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(
        category: Category,
        onCategorySelected: OnCategorySelected
    ) {
        with(binding) {
            Glide.with(root)
                .load(category.imageUrl)
                .circleCrop()
                .into(categoryImageView)
            categoryImageView.setOnClickListener {
                onCategorySelected(category)
            }
            categoryNameTextView.text= category.name
        }
    }
}