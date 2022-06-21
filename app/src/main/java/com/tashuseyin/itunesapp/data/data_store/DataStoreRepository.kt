package com.tashuseyin.itunesapp.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.common.Constant.PREFERENCES_MEDIA_TYPE
import com.tashuseyin.itunesapp.common.Constant.PREFERENCES_MEDIA_TYPE_ID
import com.tashuseyin.itunesapp.common.Constant.PREFERENCES_QUERY
import com.tashuseyin.itunesapp.domain.model.SelectMediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(Constant.PREFERENCES_NAME)

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMediaType = stringPreferencesKey(PREFERENCES_MEDIA_TYPE)
        val selectedMediaTypeId = intPreferencesKey(PREFERENCES_MEDIA_TYPE_ID)
        val query = stringPreferencesKey(PREFERENCES_QUERY)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore


    suspend fun saveMediaType(
        mediaType: String,
        mediaTypeId: Int,
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMediaType] = mediaType
            preferences[PreferenceKeys.selectedMediaTypeId] = mediaTypeId
        }
    }

    val readMediaType: Flow<SelectMediaType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMediaType =
                preferences[PreferenceKeys.selectedMediaType] ?: Constant.DEFAULT_MEDIA_TYPE
            val selectedMediaTypeId = preferences[PreferenceKeys.selectedMediaTypeId] ?: 0

            SelectMediaType(
                selectedMediaType,
                selectedMediaTypeId
            )
        }


    val readQuery: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val newQuery = it[PreferenceKeys.query] ?: ""
            newQuery
        }

    suspend fun saveQuery(query: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.query] = query
        }
    }


}