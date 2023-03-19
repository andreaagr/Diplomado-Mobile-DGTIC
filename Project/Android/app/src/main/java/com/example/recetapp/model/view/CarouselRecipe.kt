package com.example.recetapp.model.view

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient
import com.example.recetapp.model.recipe.instructions.AnalyzedInstruction

@Entity(tableName = "carousel")
data class CarouselRecipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val imageUrl: String?,
    val timeOfPreparation: Int?,
    val summary: String?,
    val cheap: Boolean?,
    val pricePerServing: Double?,
    val servings: Int?,
    val analyzedInstructions: List<AnalyzedInstruction>?,
    val diets: List<String>?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<ExtendedIngredient>?,
    val instructions: String?
)

fun CarouselRecipe.toRecipe() = Recipe(
    id,
    title,
    imageUrl,
    timeOfPreparation,
    summary,
    cheap,
    pricePerServing,
    servings,
    analyzedInstructions,
    diets,
    dishTypes,
    extendedIngredients,
    instructions
)