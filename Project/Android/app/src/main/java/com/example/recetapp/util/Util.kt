package com.example.recetapp.util

import android.text.Html
import android.text.Spanned

enum class ImageSize(val pxSize: String) {
    SMALL("100x100"),
    MEDIUM("250x250"),
    BIG("500x500")
}

enum class CategoryType(val identifier: String) {
    CUISINE("cuisine"),
    DIET("diet"),
    MEAL_TYPE("type")
}

enum class Endpoint(val identifier: String) {
    INGREDIENT("ingredients"),
    EQUIPMENT("equipment")
}

enum class ScreenResultType {
    FAVORITES,
    CATEGORIES
}

fun getImageUrl(
    imageName: String,
    endpoint: Endpoint,
    size: ImageSize
) = "https://spoonacular.com/cdn/${endpoint.identifier}_${size.pxSize}/$imageName"

fun formatFromHTML(htmlText: String): Spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
