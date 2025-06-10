package com.jin.honey.feature.datastore.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.datastore.favoriteDataStore
import com.jin.honey.feature.datastore.searchKeywordDataStore
import com.jin.honey.feature.datastore.settingDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val context = context.applicationContext

    override suspend fun completeFirstLaunch() {
        context.settingDataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }

    override suspend fun isFirstLaunch(): Boolean {
        val result = context.settingDataStore.data.first()
        return result[FIRST_LAUNCH_KEY] ?: true
    }

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
            val current = preference[FAVORITE] ?: emptySet()
            val updated = if (current.contains(menuName)) {
                current.filterNot { it == menuName }
                    .toSet()
            } else {
                listOf(menuName)
                    .plus(current)
                    .distinct()
                    .toSet()
            }

            preference[FAVORITE] = updated
        }
    }

    override fun flowFavoriteMenus(): Flow<List<String>> {
        return context.favoriteDataStore.data.map { preferences ->
            val result = preferences[FAVORITE]?.toList() ?: emptyList()
            result
        }
    }

    private companion object {
        val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")
        val RECENT_SEARCH_KEYWORD = stringSetPreferencesKey("recentSearchKeyword")
        val FAVORITE = stringSetPreferencesKey("favorite")
        val RECENTLY = stringSetPreferencesKey("recently")
    }
}
