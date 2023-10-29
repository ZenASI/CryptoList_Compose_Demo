package com.zenasi.cryptolist_compose_demo.utils


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun <T> saveDataStoreValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> readDataStoreValue(key: Preferences.Key<T>, defaultValue: T?): T {
        val result = coroutineScope {
            dataStore.data.mapNotNull {
                it[key] ?: defaultValue
            }.first()
        }
        return result
    }

    private fun checkDefaultType(type: Any): Any {
        return when (type) {
            Int -> 0
            String -> ""
            Boolean -> false
            Float -> .0f
            Long -> 0L
            else -> throw IllegalArgumentException("Type no Support:")
        }
    }
}