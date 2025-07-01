package com.jin.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jin.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val context = context.applicationContext

    override suspend fun saveSearchKeyword(menuName: String) {
        context.searchKeywordDataStore.edit { preferences ->
            val current = preferences[RECENT_SEARCH_KEYWORD] ?: emptySet()
            preferences[RECENT_SEARCH_KEYWORD] = listOf(menuName)
                .plus(current)
                .distinct()
                .take(10)
                .toSet()
        }
    }

    override suspend fun deleteSearchKeyword(menuName: String) {
        context.searchKeywordDataStore.edit { preferences ->
            val current = preferences[RECENT_SEARCH_KEYWORD] ?: emptySet()
            preferences[RECENT_SEARCH_KEYWORD] = current
                .filterNot { it == menuName }
                .toSet()
        }
    }

    override suspend fun clearSearchKeyword() {
        context.searchKeywordDataStore.edit { preferences ->
            preferences[RECENT_SEARCH_KEYWORD] = emptySet()
        }
    }

    override fun flowSearchKeywords(): Flow<List<String>> {
        return context.searchKeywordDataStore.data.map { preferences ->
            preferences[RECENT_SEARCH_KEYWORD]?.toList()?.take(10) ?: emptyList()
        }
    }

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
        val RECENT_SEARCH_KEYWORD = stringSetPreferencesKey("recentSearchKeyword")
        val FAVORITE = stringSetPreferencesKey("favorite")
        val RECENTLY = stringSetPreferencesKey("recently")
    }
}
