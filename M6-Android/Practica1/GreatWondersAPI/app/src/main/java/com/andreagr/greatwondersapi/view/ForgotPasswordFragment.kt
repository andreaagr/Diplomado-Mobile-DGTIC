package com.andreagr.greatwondersapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreagr.greatwondersapi.databinding.FragmentForgotPasswordBinding
import com.andreagr.greatwondersapi.util.UIResponseState
import com.andreagr.greatwondersapi.view.viewmodel.FirebaseAuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var _binding: FragmentForgotPasswordBinding
    private val binding get() = _binding
    private val navController by lazy { findNavController() }
    private val viewModel by viewModels<FirebaseAuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentForgotPasswordBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        with(binding) {
            passwordConfirmButton.setOnClickListener {
                viewModel.recoverPassword(editTextTextEmailAddress3.text.toString())
            }
            passwordCancelButton.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    private fun handleUIState(result: UIResponseState) {
        when(result) {
            is UIResponseState.Success<*> -> {
                binding.loadingConstraintLayout.visibility = View.GONE
                Toast.makeText(
                    activity,
                    result.content as String,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is UIResponseState.Error -> {
                binding.loadingConstraintLayout.visibility = View.GONE
                Toast.makeText(
                    activity,
                    result.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else ->
                binding.loadingConstraintLayout.visibility = View.VISIBLE
        }
    }
}