package com.jin.honey.feature.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.jin.honey.feature.datastore.PreferencesRepository
import kotlinx.coroutines.flow.first

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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

    private companion object {
        val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")
    }
}
