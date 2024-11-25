package com.example.restaurantreview.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteEvent")
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val mediaCover: String? = null,
    val beginTime: String? = null,
    val endTime: String? = null,
)