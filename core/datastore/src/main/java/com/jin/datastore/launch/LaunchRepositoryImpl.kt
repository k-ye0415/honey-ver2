package com.jin.datastore.launch

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jin.datastore.settingDataStore
import com.jin.domain.launch.LaunchRepository
import kotlinx.coroutines.flow.first

class LaunchRepositoryImpl(context: Context) : LaunchRepository {
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
