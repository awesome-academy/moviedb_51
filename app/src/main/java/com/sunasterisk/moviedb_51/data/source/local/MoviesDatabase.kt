package com.sunasterisk.moviedb_51.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunasterisk.moviedb_51.data.model.Movie
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import com.sunasterisk.moviedb_51.data.source.local.dao.MoviesRecentDao

@Database(entities = [MovieRecent::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MoviesRecentDao

    companion object {
        private var instance: MoviesDatabase? = null
        fun getInstance(context: Context) =
            instance ?: Room.databaseBuilder(context, MoviesDatabase::class.java, "database-movies")
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
    }
}
