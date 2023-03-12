package com.example.recetapp.data

import androidx.room.TypeConverter
import com.example.recetapp.model.recipe.Recipe
import com.example.recetapp.model.recipe.ingredients.ExtendedIngredient
import com.example.recetapp.model.recipe.instructions.AnalyzedInstruction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConverters {
    val gson = Gson()

    @TypeConverter
    fun stringToRecipeList(data: String?): List<Recipe?>? {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<Recipe?>?>() {}.type
        return gson.fromJson<List<Recipe?>>(data, listType)
    }

    @TypeConverter
    fun recipeListToString(recipes: List<Recipe?>?): String? {
        return gson.toJson(recipes)
    }

    @TypeConverter
    fun stringToAnalyzedInstructionList(data: String?): List<AnalyzedInstruction?>? {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<AnalyzedInstruction?>?>() {}.type
        return gson.fromJson<List<AnalyzedInstruction?>>(data, listType)
    }

    @TypeConverter
    fun analyzedInstructionListToString(extendedIngredients: List<AnalyzedInstruction?>?): String? {
        return gson.toJson(extendedIngredients)
    }

    @TypeConverter
    fun stringToExtendedIngredientList(data: String?): List<ExtendedIngredient?>? {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<ExtendedIngredient?>?>() {}.type
        return gson.fromJson<List<ExtendedIngredient?>>(data, listType)
    }

    @TypeConverter
    fun extendedIngredientListToString(extendedIngredients: List<ExtendedIngredient?>?): String? {
        return gson.toJson(extendedIngredients)
    }

    @TypeConverter
    fun stringToStringList(data: String?): List<String?>? {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String?>>(data, listType)
    }

    @TypeConverter
    fun stringListToString(stringList: List<String?>?): String? {
        return gson.toJson(stringList)
    }
}