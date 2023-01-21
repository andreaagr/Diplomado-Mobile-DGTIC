package com.andreagr.greatwondersapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreagr.greatwondersapi.model.GreatWonder
import com.andreagr.greatwondersapi.util.UIResponseState
import com.andreagr.greatwondersapi.databinding.FragmentWonderListBinding
import com.andreagr.greatwondersapi.view.adapter.GreatWonderAdapter
import com.andreagr.greatwondersapi.view.adapter.SpacingDecoration
import com.andreagr.greatwondersapi.view.viewmodel.GreatWonderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WonderListFragment : Fragment() {

    private lateinit var _binding: FragmentWonderListBinding
    private val binding get() = _binding
    private val viewModel by viewModels<GreatWonderViewModel>()

    private val wonderAdapter by lazy {
        GreatWonderAdapter { greatWonder ->
            WonderListFragmentDirections.actionWonderListFragmentToWonderDetailFragment2(greatWonder)
                .let { findNavController().navigate(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWonderListBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        binding.wonderListRecyclerView.apply {
            adapter = wonderAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(SpacingDecoration(16))
        }
    }

    private fun handleUIState(uiState: UIResponseState) {
        when (uiState) {
            is UIResponseState.Loading -> {
                //Show loader
            }
            is UIResponseState.Success<*> -> {
                wonderAdapter.submitList(uiState.content as List<GreatWonder>)
            }
            else -> {
                //Show error
            }
        }
    }
}