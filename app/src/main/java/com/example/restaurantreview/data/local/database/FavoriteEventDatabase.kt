package com.example.restaurantreview.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restaurantreview.data.local.dao.FavoriteEventDao
import com.example.restaurantreview.data.local.entity.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 1, exportSchema = false)
abstract class FavoriteEventDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteEventDatabase? = null

        fun getDatabase(context: Context): FavoriteEventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteEventDatabase::class.java,
                    "favorite_event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}