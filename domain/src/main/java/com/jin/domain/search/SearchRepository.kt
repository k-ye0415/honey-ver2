package com.jin.domain.search

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun flowSearchKeywords(): Flow<List<String>>
    suspend fun saveSearchKeyword(menuName: String)
    suspend fun deleteSearchKeyword(menuName: String)
    suspend fun clearSearchKeyword()
}
