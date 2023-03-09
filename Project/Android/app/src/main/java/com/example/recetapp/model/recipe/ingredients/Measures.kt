package com.example.recetapp.model

data class Measures(
    val metric: Metric,
    val us: Us
)

data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)