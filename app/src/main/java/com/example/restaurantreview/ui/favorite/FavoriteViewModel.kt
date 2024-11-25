package com.example.restaurantreview.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.local.entity.FavoriteEvent
import com.example.restaurantreview.data.remote.EventRepository

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = repository.getAllFavoriteEvents()
}