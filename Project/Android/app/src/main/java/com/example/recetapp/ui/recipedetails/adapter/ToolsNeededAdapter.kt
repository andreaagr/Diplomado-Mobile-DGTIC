package com.example.recetapp.ui.recipedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recetapp.Endpoint
import com.example.recetapp.ImageSize
import com.example.recetapp.R
import com.example.recetapp.databinding.CategoryItemBinding
import com.example.recetapp.getImageUrl
import com.example.recetapp.model.recipe.instructions.Ingredient
import com.example.recetapp.model.recipe.instructions.Tool

class ToolsNeededAdapter(
    private val tools: List<Tool>
): RecyclerView.Adapter<ToolNeededViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolNeededViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> CategoryItemBinding.inflate(layoutInflater, parent, false) }
            .let { categoryItemBinding ->  ToolNeededViewHolder(categoryItemBinding) }
    }

    override fun onBindViewHolder(holder: ToolNeededViewHolder, position: Int) {
        holder.bind(tools[position])
    }

    override fun getItemCount(): Int {
        return tools.size
    }
}

class ToolNeededViewHolder(
    private val binding: CategoryItemBinding
) : ViewHolder(binding.root) {

    fun bind(tool: Tool) {
        with(binding) {
            val endpoint = if (tool is Ingredient) Endpoint.INGREDIENT else Endpoint.EQUIPMENT
            val imageName = tool.imageName.ifBlank { tool.name + ".jpg" }
            Glide.with(root)
                .load(getImageUrl(imageName, endpoint, ImageSize.SMALL))
                .circleCrop()
                .placeholder(R.drawable.ingredient)
                .into(categoryImageView)
            categoryNameTextView.text= tool.name
        }
    }
}