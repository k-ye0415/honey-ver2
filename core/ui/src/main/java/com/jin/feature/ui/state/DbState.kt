package com.jin.feature.ui.state

sealed class DbState<out T> {
    object Success : DbState<Nothing>()
    data class Error(val message: String) : DbState<Nothing>()
}
