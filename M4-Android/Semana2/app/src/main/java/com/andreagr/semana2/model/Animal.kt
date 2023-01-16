package com.andreagr.semana2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal (
    val id: Long,
    val name: String,
    val image: Int
): Parcelable