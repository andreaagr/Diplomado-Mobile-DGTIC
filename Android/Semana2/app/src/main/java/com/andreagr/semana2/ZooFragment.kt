package com.andreagr.semana2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andreagr.semana2.adapter.ZooAdapter
import com.andreagr.semana2.databinding.FragmentZooBinding
import com.andreagr.semana2.model.Animal

class ZooFragment : Fragment() {

    private var _binding: FragmentZooBinding? = null
    private val binding: FragmentZooBinding get() = _binding!!
    private lateinit var zooAdapter: ZooAdapter
    private lateinit var navController: NavController
    private val zooAnimals = listOf<Animal>(
        //Animal(1, "León", )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentZooBinding
        .inflate(layoutInflater, container, false)
        .apply { _binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        zooAdapter = ZooAdapter(zooAnimals) { animalId ->
            ZooFragmentDirections
                .actionZooFragmentToAnimalDetailActivity(animalId)
                .let { navController.navigate(it) }
        }

        navController = findNavController()
        binding.animalsRecyclerView.run {
            adapter = zooAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

}