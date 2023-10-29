package com.zenasi.cryptolist_compose_demo.activity

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenasi.cryptolist_compose_demo.Def
import com.zenasi.cryptolist_compose_demo.utils.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(private val dataStoreUtils: DataStoreUtils) : ViewModel() {

    val TAG = this::class.java.simpleName

    var nightModeLiveData = MutableStateFlow(false)
    var livePriceUpdate = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            nightModeLiveData.value = dataStoreUtils.readDataStoreValue(
                booleanPreferencesKey(Def.NightModeYes.name),
                false
            )
        }
    }

    fun setNightModeValue(isNighMode: Boolean) {
        nightModeLiveData.value = isNighMode
        viewModelScope.launch {
            dataStoreUtils.saveDataStoreValue(
                booleanPreferencesKey(Def.NightModeYes.name),
                isNighMode
            )
        }
    }
}