package com.qsoft.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.qsoft.datastore.dataSource.IPreferenceDataStoreAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.IOException

private const val DATASTORE_NAME = "TaskDataStore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { emptyPreferences() }
    )
)


class PreferenceDataStoreHelper(@ApplicationContext private val context: Context) :
    IPreferenceDataStoreAPI {

    private val dataSource = context.dataStore

    private fun clearCorruptedDataStore() {
        try {
            val dataStoreFile = File(context.filesDir, "datastore/${DATASTORE_NAME}.preferences_pb")
            if (dataStoreFile.exists()) {
                dataStoreFile.delete()
            }
        } catch (_: Exception) {

        }
    }

    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataSource.data.catch { exception ->
            when (exception) {
                is CorruptionException -> {
                    clearCorruptedDataStore()
                    emit(emptyPreferences())
                }

                is IOException -> {
                    emit(emptyPreferences())
                }

                else -> throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }.distinctUntilChanged()



    override suspend fun <T> savePreference(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataSource.edit { prefs ->
            prefs[key] = value
        }
    }

}