package com.example.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.restaurantreview.data.remote.response.EventResponse
import com.example.restaurantreview.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _eventResponse = MutableLiveData<EventResponse>()
    val eventResponse: LiveData<EventResponse> get() = _eventResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getEvents(active: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _eventResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }
}