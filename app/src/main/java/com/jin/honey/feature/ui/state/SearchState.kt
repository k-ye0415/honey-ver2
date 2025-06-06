package com.jin.honey.feature.ui.state

sealed class SearchState<out T> {
    object Idle : SearchState<Nothing>()
    object Loading : SearchState<Nothing>()
    data class Success<T>(val data: T) : SearchState<T>()
    data class Error(val message: String) : SearchState<Nothing>()
}
