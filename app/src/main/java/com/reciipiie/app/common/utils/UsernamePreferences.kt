package com.reciipiie.app.common.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsernamePreferences(val context: Context) {

    companion object {
        private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("user")
        val USER_NAME = stringPreferencesKey("Name")
    }

    // Get the username as a Flow
    val getUserName: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }

    // Save the username
    suspend fun saveUserName(name: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    // Clear the username
    suspend fun clear() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
