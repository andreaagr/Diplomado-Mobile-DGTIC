package com.example.recetapp.ui.recipedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetapp.databinding.FragmentRecipeInstructionsBinding
import com.example.recetapp.model.recipe.instructions.AnalyzedInstruction
import com.example.recetapp.ui.recipedetails.adapter.AnalyzedInstructionsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeInstructionsFragment : Fragment() {

    private var _binding: FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!
    private val gson = Gson()
    private val instructions: List<AnalyzedInstruction>
        get() = gson.fromJson(requireArguments().getString(ARG_INSTRUCTIONS), object : TypeToken<List<AnalyzedInstruction>>() {}.type)
            ?: throw IllegalArgumentException("Argument $ARG_INSTRUCTIONS required")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRecipeInstructionsBinding.inflate(inflater, container, false)
            .apply { _binding =  this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailedInstructionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AnalyzedInstructionsAdapter(instructions)
        }
    }

    companion object {
        private const val ARG_INSTRUCTIONS = "argInstructionList"

        fun newInstance(instructions: List<AnalyzedInstruction>) =
            RecipeInstructionsFragment().apply {
                arguments = bundleOf(
                    ARG_INSTRUCTIONS to gson.toJson(instructions)
                )
            }
    }
}