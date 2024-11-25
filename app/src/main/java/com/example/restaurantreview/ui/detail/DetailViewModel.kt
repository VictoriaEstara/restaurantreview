package com.example.restaurantreview.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreview.data.local.entity.FavoriteEvent
import com.example.restaurantreview.data.remote.response.DetailEventResponse
import com.example.restaurantreview.data.remote.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: EventRepository) : ViewModel() {
    private val _detailEvent = MutableLiveData<DetailEventResponse?>()
    val detailEvent: LiveData<DetailEventResponse?> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk status favorite
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getDetailEvent(id: String) {
        if (_detailEvent.value != null) return

        // metode PostValue untuk memperbarui LiveData
        _isLoading.postValue(true)
        repository.getDetailEvent(id, _detailEvent, {
            // Berhasil dapat data
            _isLoading.postValue(false)
        }, {
            // Gagal dapat data
            _isLoading.postValue(false)
        })

        // Cek status favorite setelah permintaan API
        checkFavoriteStatus(id)
    }

    private fun checkFavoriteStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(repository.isFavorite(id))
        }
    }

    fun insertFavoriteEvent(event: FavoriteEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteEvent(event)
            _isFavorite.postValue(true)
            checkFavoriteStatus(event.id)
        }
    }

    fun deleteFavoriteEvent(event: FavoriteEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteEvent(event)
            _isFavorite.postValue(false)
            checkFavoriteStatus(event.id)
        }
    }
}
