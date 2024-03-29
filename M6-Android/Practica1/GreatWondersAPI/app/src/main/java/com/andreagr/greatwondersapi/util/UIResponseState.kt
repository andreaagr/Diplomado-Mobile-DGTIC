package com.andreagr.greatwondersapi.util

sealed class UIResponseState {
    object Loading: UIResponseState()
    object Waiting : UIResponseState()
    data class Error(val errorMessage: String): UIResponseState()
    data class Success<T>(val content: T): UIResponseState()
}