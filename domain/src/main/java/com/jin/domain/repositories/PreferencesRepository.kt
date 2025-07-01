package com.jin.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun insertOrUpdateFavoriteMenu(menuName: String)
    fun flowFavoriteMenus(): Flow<List<String>>
    suspend fun insertRecentlyMenu(menuName: String)
    fun flowRecentlyMenus(): Flow<List<String>>
    suspend fun deleteRecentlyMenu(menuName: String)
}
