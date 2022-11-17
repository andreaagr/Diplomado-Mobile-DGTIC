package com.andreagr.semana3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andreagr.semana3.ZooAdapter
import com.andreagr.semana3.ZooViewModel
import com.andreagr.semana3.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ZooViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val zooAdapter by lazy { ZooAdapter { id ->
        ListFragmentDirections
            .actionListFragmentToAnimalDetailFragment(
                viewModel.zooList.value?.first{it.id == id}!!
            ).let { navController.navigate(it) }
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.zooList.observe(viewLifecycleOwner) {
            //viewModel.syncAnimals()
            zooAdapter.submitList(it)
        }
        return FragmentListBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.syncAnimals()
        binding.zooListRecyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = zooAdapter
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToAddAnimalFragment())
        }
    }
}