package com.andreagr.greatwondersapi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreagr.greatwondersapi.databinding.FragmentLoginBinding
import com.andreagr.greatwondersapi.util.UIResponseState
import com.andreagr.greatwondersapi.view.viewmodel.FirebaseAuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    private val viewModel by viewModels<FirebaseAuthViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLoginBinding
            .inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        with(binding) {
            logInButton.setOnClickListener {
                viewModel.authenticateUser(
                    editTextTextEmailAddress.text.toString(),
                    editTextTextPassword.text.toString()
                )
            }
            newAccountTextView.setOnClickListener {
                LoginFragmentDirections
                    .actionLoginFragmentToCreateAccountFragment()
                    .let { navController.navigate(it) }
            }
            forgotPasswordTextView.setOnClickListener {
                LoginFragmentDirections
                    .actionLoginFragmentToForgotPasswordFragment()
                    .let { navController.navigate(it) }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.getCurrentUser() != null) {
            LoginFragmentDirections
                .actionLoginFragmentToWonderListFragment()
                .let { navController.navigate(it) }
        }
    }

    private fun handleUIState(result: UIResponseState) {
        when(result) {
            is UIResponseState.Success<*> -> {
                binding.loadingConstraintLayout.visibility = View.GONE
                LoginFragmentDirections
                    .actionLoginFragmentToWonderListFragment()
                    .let { navController.navigate(it) }
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