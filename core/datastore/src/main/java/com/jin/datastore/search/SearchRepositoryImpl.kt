package com.jin.datastore.search

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jin.datastore.searchKeywordDataStore
import com.jin.domain.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(context: Context) : SearchRepository {
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

    private companion object {
        val RECENT_SEARCH_KEYWORD = stringSetPreferencesKey("recentSearchKeyword")
    }
}
