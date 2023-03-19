package com.example.recetapp.ui.recipedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recetapp.databinding.InstructionStepRowBinding
import com.example.recetapp.model.recipe.instructions.Step
import com.example.recetapp.model.recipe.instructions.Tool

class StepAdapter(
    private val steps: List<Step>
) : RecyclerView.Adapter<StepInstructionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepInstructionViewHolder {
        return LayoutInflater.from(parent.context)
            .let { layoutInflater -> InstructionStepRowBinding.inflate(layoutInflater, parent, false) }
            .let { instructionRowBinding -> StepInstructionViewHolder(instructionRowBinding) }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: StepInstructionViewHolder, position: Int) {
        holder.bind(steps[position])
    }
}

class StepInstructionViewHolder(
    private val binding: InstructionStepRowBinding
) : ViewHolder(binding.root) {

    fun bind(step: Step) {
        with(binding) {
            stepNumberTextView.text = step.number.toString()
            stepInstructionTextView.text = step.step
            step.ingredients?.let {
                setSectionVisibility(
                    ingredientsTextView,
                    stepIngredientsRecyclerView,
                    it
                )
            }
            step.equipmentList?.let {
                setSectionVisibility(
                    equipmentTextView,
                    stepEquipmentRecyclerView,
                    it
                )
            }

        }
    }

    private fun setSectionVisibility(
        titleTextView: TextView,
        recyclerView: RecyclerView,
        tools: List<Tool>
    ) {
        if (tools.isEmpty()) {
            titleTextView.visibility = View.GONE
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(binding.root.context, HORIZONTAL, false)
                adapter = ToolsNeededAdapter(tools)
            }
        }
    }
}