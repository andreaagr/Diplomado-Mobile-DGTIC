package com.example.recetapp.model.recipe.instructions

import com.google.gson.annotations.SerializedName

data class AnalyzedInstruction(
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    @SerializedName("equipment")
    val equipmentList: List<Equipment>,
    val length: StepLength
)

data class StepLength (
    val number: Int,
    val unit: String
)

