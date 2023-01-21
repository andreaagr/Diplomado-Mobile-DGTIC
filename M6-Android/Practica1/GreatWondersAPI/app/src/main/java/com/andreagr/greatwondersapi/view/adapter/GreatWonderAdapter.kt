package com.andreagr.greatwondersapi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreagr.greatwondersapi.databinding.WonderCardItemBinding
import com.andreagr.greatwondersapi.model.GreatWonder
import com.andreagr.greatwondersapi.util.showImage

typealias OnElementClicked = (GreatWonder) -> Unit

class GreatWonderAdapter(
    private val onElementClicked: OnElementClicked
) : ListAdapter<GreatWonder, GreatWonderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreatWonderViewHolder {
        return  LayoutInflater.from(parent.context)
            .let { layoutInflater -> WonderCardItemBinding.inflate(layoutInflater, parent, false)  }
            .let { zooItemBinding -> GreatWonderViewHolder(zooItemBinding, onElementClicked) }
    }

    override fun onBindViewHolder(holder: GreatWonderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GreatWonder>() {
            override fun areItemsTheSame(oldItem: GreatWonder, newItem: GreatWonder): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GreatWonder, newItem: GreatWonder): Boolean =
                oldItem == newItem
        }
    }
}

class GreatWonderViewHolder(
    private val binding: WonderCardItemBinding,
    private val onElementClicked: OnElementClicked
): RecyclerView.ViewHolder(binding.root) {

    fun bind(greatWonder: GreatWonder) {
        val location =
            if (!greatWonder.location.city.isNullOrEmpty())
                greatWonder.location.city + ", " + greatWonder.location.country
            else
                greatWonder.location.country
        with(binding) {
            wonderNameTextView.text = greatWonder.name
            wonderLocationTextView.text = location
            showImage(greatWonder.image, wonderImageView, root.context)
            wonderCardContainer.setOnClickListener { onElementClicked(greatWonder) }
        }
    }
}