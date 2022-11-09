package com.andreagr.semana2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreagr.semana2.databinding.ZooItemBinding
import com.andreagr.semana2.model.Animal

typealias OnAnimalClicked = (Long) -> Unit

class ZooAdapter(
    private val animals: List<Animal>,
    private val OnAnimalClicked: OnAnimalClicked
): RecyclerView.Adapter<AnimalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .let { layoutInflater -> ZooItemBinding.inflate(layoutInflater, parent, false) }
            .let { zooItemBinding -> AnimalViewHolder(zooItemBinding, OnAnimalClicked) }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(animals[position])
    }

    override fun getItemCount() = animals.size
}

class AnimalViewHolder(
    private val binding: ZooItemBinding,
    private val OnAnimalClicked: OnAnimalClicked
): RecyclerView.ViewHolder(binding.root) {
    fun bind(animal: Animal) {
        with(binding) {
            animalImageView.setImageResource(animal.image)
            animalNameTextView.text = animal.name
            animalCardView.setOnClickListener { OnAnimalClicked.invoke(animal.id) }
        }
    }
}
