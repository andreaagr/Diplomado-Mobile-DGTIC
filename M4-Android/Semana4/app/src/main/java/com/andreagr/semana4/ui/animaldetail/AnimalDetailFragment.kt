package com.andreagr.semana4.ui.animaldetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.andreagr.semana4.R
import com.andreagr.semana4.databinding.FragmentAnimalDetailBinding
import com.bumptech.glide.Glide

class AnimalDetailFragment : Fragment() {

    private val args by navArgs<AnimalDetailFragmentArgs>()
    private lateinit var _binding: FragmentAnimalDetailBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimalDetailBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()
        bundle.putString("description", args.animal.description)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.animalContent) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_details, bundle)

        with(binding) {
            Glide.with(binding.root)
                .load(args.animal.image)
                .centerCrop()
                .placeholder(R.drawable.ic_img_placeholder)
                .into(imageView)

            buttonDetails.setOnClickListener {
                buttonDetails.isEnabled = false
                buttonDescription.isEnabled = true
                DescriptionFragmentDirections.actionDescriptionFragmentToDetailsFragment(args.animal)
                    .let { navController.navigate(it) }
            }

            buttonDescription.setOnClickListener {
                buttonDescription.isEnabled = false
                buttonDetails.isEnabled = true
                DetailsFragmentDirections.actionDetailsFragmentToDescriptionFragment(args.animal.description)
                    .let { navController.navigate(it) }
            }
        }

    }
}