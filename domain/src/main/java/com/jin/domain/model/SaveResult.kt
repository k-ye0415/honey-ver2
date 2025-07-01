package com.jin.domain.model

sealed class SaveResult {
    object Saved : SaveResult()
    data class Full(val message: String) : SaveResult()
    data class Error(val message: String) : SaveResult()
}
