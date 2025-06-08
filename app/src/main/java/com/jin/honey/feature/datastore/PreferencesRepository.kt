package com.jin.honey.feature.datastore

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
    suspend fun saveSearchKeyword(menuName: String)
    fun findSearchKeywords(): Flow<List<String>>
}
