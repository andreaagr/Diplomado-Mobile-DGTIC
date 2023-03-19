package com.example.recetapp.model.recipe.instructions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
): Parcelable

@Parcelize
data class Step(
    val number: Int?,
    val step: String?,
    val ingredients: List<Ingredient>?,
    @SerializedName("equipment")
    val equipmentList: List<Equipment>?,
    val length: StepLength?
): Parcelable

@Parcelize
data class StepLength (
    val number: Int?,
    val unit: String?
): Parcelable

