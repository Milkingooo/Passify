package com.example.passify.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsMaster(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val IS_PASSKEY_ENABLED = booleanPreferencesKey("is_passkey_enabled")
    }

    suspend fun setIsPasskeyEnabled(isDarkModeEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_PASSKEY_ENABLED] = isDarkModeEnabled
        }
    }

    val isPasskeyEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_PASSKEY_ENABLED] ?: false
    }
}