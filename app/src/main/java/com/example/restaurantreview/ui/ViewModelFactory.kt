package com.example.restaurantreview.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantreview.ui.settings.ThemePreferences
import com.example.restaurantreview.data.local.database.FavoriteEventDatabase
import com.example.restaurantreview.data.remote.retrofit.ApiConfig
import com.example.restaurantreview.data.remote.EventRepository
import com.example.restaurantreview.ui.detail.DetailViewModel
import com.example.restaurantreview.ui.favorite.FavoriteViewModel
import com.example.restaurantreview.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(
    private val repository: EventRepository,
    private val preferences: ThemePreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(preferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val database = FavoriteEventDatabase.getDatabase(context)
                val favoriteEventDao = database.favoriteEventDao()
                val apiService = ApiConfig.getApiService()
                val repository = EventRepository(apiService, favoriteEventDao)
                val preferences = ThemePreferences(context) // Buat instance ThemePreferences
                instance ?: ViewModelFactory(repository, preferences).also { instance = it }
            }
    }
}