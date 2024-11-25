package com.example.restaurantreview.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.restaurantreview.data.local.entity.FavoriteEvent

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: FavoriteEvent)

    @Delete
    fun delete(event: FavoriteEvent)

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    suspend fun getFavoriteEventById(id: String): FavoriteEvent? // Mengembalikan FavoriteEvent?

    @Query("SELECT * FROM FavoriteEvent ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>
}