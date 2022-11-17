package com.andreagr.semana3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreagr.semana3.databinding.ZooItemBinding
import com.andreagr.semana3.model.ZooAnimal
import com.bumptech.glide.Glide

typealias OnAnimalClicked = (Int) -> Unit

class ZooAdapter(
    private val OnAnimalClicked: OnAnimalClicked
): ListAdapter<ZooAnimal, AnimalViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> ZooItemBinding.inflate(layoutInflater, parent, false)  }
            .let { zooItemBinding -> AnimalViewHolder(zooItemBinding, OnAnimalClicked)  }
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ZooAnimal>() {
            override fun areItemsTheSame(oldItem: ZooAnimal, newItem: ZooAnimal): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ZooAnimal, newItem: ZooAnimal): Boolean =
                oldItem == newItem
        }
    }
}

class AnimalViewHolder(
    private val binding: ZooItemBinding,
    private val OnAnimalClicked: OnAnimalClicked
): RecyclerView.ViewHolder(binding.root) {
    fun bind(animal: ZooAnimal) {
        with(binding) {
            Glide.with(binding.root)
                .load(animal.image)
                .centerCrop()
                .placeholder(R.drawable.ic_img_placeholder)
                .into(binding.animalImageView)
            animalNameTextView.text = animal.name
            animalCardView.setOnClickListener { OnAnimalClicked.invoke(animal.id) }
        }
    }
}
