package com.example.recetapp.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient
import com.example.recetapp.model.recipe.instructions.AnalyzedInstruction
import com.google.gson.annotations.SerializedName

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("readyInMinutes")
    val timeOfPreparation: Int,
    val summary: String,
    val cheap: Boolean,
    val pricePerServing: Double,
    val servings: Int,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val instructions: String
)