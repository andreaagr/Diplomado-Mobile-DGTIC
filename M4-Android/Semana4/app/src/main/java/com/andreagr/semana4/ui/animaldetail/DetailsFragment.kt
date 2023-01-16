package com.andreagr.semana4.ui.animaldetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.andreagr.semana4.R
import com.andreagr.semana4.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var _binding: FragmentDetailsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val sex = resources.getString(R.string.animal_sex) + args.animal.sex
            val weight = resources.getString(R.string.animal_weight) + args.animal.weight
            sexTextView.text = sex
            weightTextView.text = weight
        }
    }
}