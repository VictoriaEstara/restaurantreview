package com.example.restaurantreview.data.remote

import androidx.lifecycle.LiveData
import com.example.restaurantreview.data.local.dao.FavoriteEventDao
import com.example.restaurantreview.data.local.entity.FavoriteEvent
import com.example.restaurantreview.data.remote.response.DetailEventResponse
import com.example.restaurantreview.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData

class EventRepository(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao
) {

    fun getDetailEvent(id: String, liveData: MutableLiveData<DetailEventResponse?>, onSuccess: () -> Unit, onFailure: () -> Unit
    ) {
        val client = apiService.getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                    onSuccess()  // Callback jika berhasil
                } else {
                    liveData.value = null
                    onFailure()  // Callback jika gagal
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                liveData.value = null
                onFailure()  // Callback jika gagal
            }
        })
    }



    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        return favoriteEventDao.getAllFavoriteEvents()
    }

    suspend fun isFavorite(eventId: String): Boolean {
        return favoriteEventDao.getFavoriteEventById(eventId) != null
    }

    suspend fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        favoriteEventDao.insert(favoriteEvent)
    }

    suspend fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        favoriteEventDao.delete(favoriteEvent)
    }
}