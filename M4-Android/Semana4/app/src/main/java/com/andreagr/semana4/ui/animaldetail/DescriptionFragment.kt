package com.andreagr.semana4.ui.animaldetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andreagr.semana4.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private val args by navArgs<DescriptionFragmentArgs>()
    private lateinit var _binding: FragmentDescriptionBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentDescriptionBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            animalDescriptionTextView.text = args.description
        }
    }

}