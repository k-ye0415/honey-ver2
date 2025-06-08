package com.jin.honey.feature.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val Context.searchKeywordDataStore: DataStore<Preferences> by preferencesDataStore(name = "recentSearchKeyword")
