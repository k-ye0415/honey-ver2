package com.jin.honey.feature.datastore

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
    fun flowSearchKeywords(): Flow<List<String>>
    suspend fun saveSearchKeyword(menuName: String)
    suspend fun deleteSearchKeyword(menuName: String)
    suspend fun clearSearchKeyword()
    suspend fun insertOrUpdateFavoriteMenu(menuName: String)
    fun flowFavoriteMenus(): Flow<List<String>>
}
