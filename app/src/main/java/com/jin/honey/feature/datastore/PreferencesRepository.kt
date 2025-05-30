package com.jin.honey.feature.datastore

interface PreferencesRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
}
