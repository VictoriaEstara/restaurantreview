package com.example.restaurantreview.data.remote.retrofit

import com.example.restaurantreview.data.remote.response.DetailEventResponse
import com.example.restaurantreview.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>
}