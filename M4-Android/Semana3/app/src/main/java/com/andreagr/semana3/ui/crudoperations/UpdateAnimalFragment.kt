package com.andreagr.semana3.ui.crudoperations

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.andreagr.semana3.R
import com.andreagr.semana3.databinding.FragmentAddAnimalBinding
import com.andreagr.semana3.model.ZooAnimal
import com.andreagr.semana3.ui.viewmodel.ZooViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateAnimalFragment : Fragment() {

    private val viewModel: ZooViewModel by viewModels()
    private lateinit var _binding: FragmentAddAnimalBinding
    private val binding: FragmentAddAnimalBinding get() = _binding
    private val args: UpdateAnimalFragmentArgs by navArgs()
    private val animal by lazy { args.animal }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentAddAnimalBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            editTextName.text = SpannableStringBuilder(animal.name)
            editTextImage.text = SpannableStringBuilder(animal.image)
            activity?.let {
                Glide.with(it)
                    .load(animal.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_img_placeholder)
                    .into(imageView)
            }
            button.text = activity?.resources?.getString(R.string.button_label_update) ?: "Actualizar"
            button.setOnClickListener {
                viewModel.updateAnimal(
                    ZooAnimal(
                        animal.id,
                        editTextName.text.toString(),
                        editTextImage.text.toString()
                    )
                )
                Toast.makeText(activity, "Elemento actualizado", Toast.LENGTH_SHORT).show()
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