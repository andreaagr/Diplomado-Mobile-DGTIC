package com.andreagr.semana3.ui.crudoperations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andreagr.semana3.databinding.FragmentAnimalDetailBinding
import com.andreagr.semana3.ui.viewmodel.ZooViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalDetailFragment : Fragment() {

    private val viewModel: ZooViewModel by viewModels()
    private lateinit var _binding: FragmentAnimalDetailBinding
    private val binding: FragmentAnimalDetailBinding get() = _binding
    private val args: AnimalDetailFragmentArgs by navArgs()
    private val animal by lazy { args.animal }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentAnimalDetailBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nameTextView.text = animal.name
            activity?.let {
                Glide.with(it).load(animal.image).centerCrop().into(animalPhotoImageView)
            }
            buttonDelete.setOnClickListener {
                viewModel.deleteAnimal(animal.id)
                Toast.makeText(activity, "Elemento eliminado", Toast.LENGTH_SHORT).show()
            }
            buttonUpdate.setOnClickListener {
                findNavController().navigate(
                    AnimalDetailFragmentDirections.actionAnimalDetailFragmentToUpdateAnimalFragment(animal)
                )
            }
        }
    }
}