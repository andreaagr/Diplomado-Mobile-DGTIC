package com.example.recetapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recetapp.databinding.FragmentHomeBinding
import com.example.recetapp.model.response.RandomRecipeResponse
import com.example.recetapp.ui.UIResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.onCreate()
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleUIState(uiState: UIResponseState?) {
        when(uiState) {
            is UIResponseState.Success<*> -> {
                if (uiState.content is RandomRecipeResponse) {
                    // Random recipes call
                }
            }
            is UIResponseState.Error -> { Log.d("Error", uiState.errorMessage)}
            else -> {
                // When loading
            }
        }
    }
}