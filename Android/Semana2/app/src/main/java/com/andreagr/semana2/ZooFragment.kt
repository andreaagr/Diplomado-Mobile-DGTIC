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
    private val zooAnimals = listOf(
        Animal(1, "Elefante", R.drawable.elefante),
        Animal(2, "Koala", R.drawable.coala),
        Animal(3, "León", R.drawable.leon),
        Animal(4, "Mono", R.drawable.mono),
        Animal(5, "Jirafa", R.drawable.jirafa),
        Animal(6, "Delfín", R.drawable.delfin),
        Animal(7, "Hipopotamo", R.drawable.hipopotamo),
        Animal(8, "Rinoceronte", R.drawable.rinoceronte)
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
                .actionZooFragment2ToAnimalDetailActivity(zooAnimals[animalId.toInt()].name)
                .let { navController.navigate(it) }
        }

        navController = findNavController()
        binding.animalsRecyclerView.apply {
            adapter = zooAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

}