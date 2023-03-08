package com.example.recetapp.ui

sealed class UIResponseState {
    object Loading: UIResponseState()
    data class Error(val errorMessage: String): UIResponseState()
    data class Success<T>(val content: T): UIResponseState()
}