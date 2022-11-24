package com.andreagr.semana4.ui.listanimals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreagr.semana4.databinding.FragmentListAnimalsBinding
import com.andreagr.semana4.viewmodel.AnimalViewModel

class ListAnimalsFragment : Fragment() {

    private val viewModel: AnimalViewModel by viewModels()

    private lateinit var _binding: FragmentListAnimalsBinding
    private val binding get() = _binding
    private val animalAdapter by lazy {
        AnimalAdapter { animal ->
            ListAnimalsFragmentDirections
                .actionListAnimalsFragmentToAnimalDetailFragment(animal, animal.name)
                .let { navController.navigate(it) }
        }
    }
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListAnimalsBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.zooList.observe(viewLifecycleOwner) {
            animalAdapter.submitList(it)
        }
        binding.animalListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = animalAdapter
        }
    }

}