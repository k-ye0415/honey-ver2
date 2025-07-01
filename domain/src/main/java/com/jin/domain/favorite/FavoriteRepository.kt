package com.jin.domain.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertOrUpdateFavoriteMenu(menuName: String)
    fun flowFavoriteMenus(): Flow<List<String>>
    suspend fun insertRecentlyMenu(menuName: String)
    fun flowRecentlyMenus(): Flow<List<String>>
    suspend fun deleteRecentlyMenu(menuName: String)
}
