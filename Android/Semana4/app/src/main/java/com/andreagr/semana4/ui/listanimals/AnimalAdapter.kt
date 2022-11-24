package com.andreagr.semana4.ui.listanimals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreagr.semana4.R
import com.andreagr.semana4.databinding.ZooItemBinding
import com.andreagr.semana4.model.Animal
import com.bumptech.glide.Glide

typealias OnAnimalClicked = (Animal) -> Unit

class AnimalAdapter(
    private val onAnimalClicked: OnAnimalClicked
) : ListAdapter<Animal, AnimalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> ZooItemBinding.inflate(layoutInflater, parent, false)  }
            .let { zooItemBinding -> AnimalViewHolder(zooItemBinding, onAnimalClicked) }
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Animal>() {
            override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean =
                oldItem == newItem
        }
    }
}

class AnimalViewHolder(
    private val binding: ZooItemBinding,
    private val OnAnimalClicked: OnAnimalClicked
): RecyclerView.ViewHolder(binding.root) {

    fun bind(animal: Animal) {
        with(binding) {
            animalNameTextView.text = animal.name
            animalDescriptionTextView.text = animal.description
            Glide.with(binding.root)
                .load(animal.image)
                .centerCrop()
                .placeholder(R.drawable.ic_img_placeholder)
                .into(binding.animalImageView)
            animalCardView.setOnClickListener { OnAnimalClicked.invoke(animal) }
        }
    }
}