package com.qsoft.productapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qsoft.datastore.PreferenceDataStoreConstants
import com.qsoft.datastore.PreferenceDataStoreHelper
import com.qsoft.designsystem.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : ViewModel() {
    val themeMode: StateFlow<ThemeMode> = run {
        val flow = MutableStateFlow(ThemeMode.SYSTEM)

        viewModelScope.launch {
            preferenceDataStoreHelper.getPreference(
                PreferenceDataStoreConstants.THEME,
                ThemeMode.SYSTEM.name
            ).map { ThemeMode.valueOf(it) }
                .collect { flow.value = it }
        }

        flow
    }
}