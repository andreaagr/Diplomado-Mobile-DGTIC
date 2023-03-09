package com.example.recetapp

enum class ImageSize(val pxSize: String) {
    SMALL("100x100"),
    MEDIUM("250x250"),
    BIG("500x500")
}

enum class Endpoint(val identifier: String) {
    INGREDIENT("ingredients"),
    EQUIPMENT("equipment")
}

fun getImageUrl(
    imageName: String,
    endpoint: Endpoint,
    size: ImageSize
) = "https://spoonacular.com/cdn/${endpoint}_${size}/$imageName"
