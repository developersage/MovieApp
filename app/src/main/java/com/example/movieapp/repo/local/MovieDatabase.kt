package com.example.movieapp.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.model.MovieData

@Database(entities = [MovieData::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}