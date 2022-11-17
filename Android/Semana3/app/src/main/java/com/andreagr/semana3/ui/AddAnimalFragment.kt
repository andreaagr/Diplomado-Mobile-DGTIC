package com.andreagr.semana3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreagr.semana3.R
import com.andreagr.semana3.ZooViewModel
import com.andreagr.semana3.databinding.FragmentAddAnimalBinding
import com.andreagr.semana3.model.ZooAnimal
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAnimalFragment : Fragment() {

    private val viewModel: ZooViewModel by viewModels()
    private lateinit var _binding: FragmentAddAnimalBinding
    private val binding: FragmentAddAnimalBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentAddAnimalBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            button.setOnClickListener {
                viewModel.addAnimal(
                    ZooAnimal(
                        name = editTextName.text.toString(),
                        image = editTextImage.text.toString()
                    )
                )
                Toast.makeText(activity, "Elemento añadido", Toast.LENGTH_SHORT).show()
            }

            editTextImage.setOnFocusChangeListener { view, b ->
                if (!b) {
                    activity?.let {
                        Glide.with(it)
                            .load(editTextImage.text.toString())
                            .centerCrop()
                            .placeholder(R.drawable.ic_img_placeholder)
                            .into(imageView)
                    }
                }
            }
        }
    }


}