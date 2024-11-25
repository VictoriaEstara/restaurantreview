package com.example.restaurantreview.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferences: ThemePreferences) : ViewModel() {

    val isDarkMode: LiveData<Boolean> = preferences.isDarkModeFlow.asLiveData()

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
        }
    }
}