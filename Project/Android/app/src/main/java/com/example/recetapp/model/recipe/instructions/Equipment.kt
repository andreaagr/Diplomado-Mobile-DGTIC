package com.example.recetapp.model.recipe.instructions

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    override val name: String,
    @SerializedName("image")
    override val imageName: String,
): Parcelable, Tool
