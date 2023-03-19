package com.example.recetapp.ui.recipedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recetapp.databinding.InstructionRowBinding
import com.example.recetapp.model.recipe.instructions.AnalyzedInstruction
import com.example.recetapp.ui.SpacingDecoration

class AnalyzedInstructionsAdapter(
  private val analyzedInstructions: List<AnalyzedInstruction>
) : RecyclerView.Adapter<AnalyzedInstructionsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnalyzedInstructionsViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> InstructionRowBinding.inflate(layoutInflater, parent, false)}
            .let { instructionRowBinding -> AnalyzedInstructionsViewHolder(instructionRowBinding) }
    }

    override fun getItemCount(): Int {
        return analyzedInstructions.size
    }

    override fun onBindViewHolder(holder: AnalyzedInstructionsViewHolder, position: Int) {
        holder.bind(analyzedInstructions[position])
    }

}

class AnalyzedInstructionsViewHolder(
    private val binding: InstructionRowBinding
) : ViewHolder(binding.root) {

    fun bind(analyzedInstruction: AnalyzedInstruction) {
        with(binding) {
            if (analyzedInstruction.name.isNullOrEmpty()) {
                instructionsNameTextView.visibility = View.GONE
            } else {
                instructionsNameTextView.text = analyzedInstruction.name
            }
            if (!analyzedInstruction.steps.isNullOrEmpty()) {
                stepsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(binding.root.context)
                    adapter = StepAdapter(analyzedInstruction.steps)
                    addItemDecoration(DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))
                }
            }
        }
    }
}