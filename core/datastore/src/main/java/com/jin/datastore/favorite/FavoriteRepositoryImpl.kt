package com.jin.datastore.favorite

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jin.datastore.favoriteDataStore
import com.jin.domain.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(context: Context) : FavoriteRepository {
    private val context = context.applicationContext

    override suspend fun insertOrUpdateFavoriteMenu(menuName: String) {
        context.favoriteDataStore.edit { preference ->
            val favoriteCurrent = preference[FAVORITE] ?: emptySet()
            val recentlyCurrent = preference[RECENTLY] ?: emptySet()

            val recentlyUpdate = if (recentlyCurrent.contains(menuName)) {
                recentlyCurrent.filterNot { it == menuName }
                    .toSet()
            } else {
                recentlyCurrent
            }

            val favoriteUpdate = if (favoriteCurrent.contains(menuName)) {
                favoriteCurrent.filterNot { it == menuName }
                    .toSet()
            } else {
                listOf(menuName)
                    .plus(favoriteCurrent)
                    .distinct()
                    .toSet()
            }

            preference[FAVORITE] = favoriteUpdate
            preference[RECENTLY] = recentlyUpdate
        }
    }

    override fun flowFavoriteMenus(): Flow<List<String>> {
        return context.favoriteDataStore.data.map { preferences ->
            val result = preferences[FAVORITE]?.toList() ?: emptyList()
            result
        }
    }

    override suspend fun insertRecentlyMenu(menuName: String) {
        context.favoriteDataStore.edit { preference ->
            val currentRecently = preference[RECENTLY] ?: emptySet()
            val currentFavorite = preference[FAVORITE] ?: emptySet()

            val updated = if (currentFavorite.contains(menuName)) {
                currentRecently
            } else {
                listOf(menuName)
                    .plus(currentRecently)
                    .distinct()
                    .toSet()
            }

            preference[RECENTLY] = updated
        }
    }

    override fun flowRecentlyMenus(): Flow<List<String>> {
        return context.favoriteDataStore.data.map { preference ->
            val result = preference[RECENTLY]?.toList() ?: emptyList()
            result
        }
    }

    override suspend fun deleteRecentlyMenu(menuName: String) {
        context.favoriteDataStore.edit { preferences ->
            val current = preferences[RECENTLY] ?: emptySet()
            preferences[RECENTLY] = current
                .filterNot { it == menuName }
                .toSet()
        }
    }

    private companion object {
        val FAVORITE = stringSetPreferencesKey("favorite")
        val RECENTLY = stringSetPreferencesKey("recently")
    }
}
