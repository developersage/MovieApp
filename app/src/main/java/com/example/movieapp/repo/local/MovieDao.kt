package com.example.movieapp.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.MovieData
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): Flow<List<MovieData>>

    @Query("SELECT * FROM movie_list WHERE id = :movie_id")
    fun getMovie(movie_id: Int): Flow<MovieData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRow(movie: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieData>)

    @Query("DELETE FROM movie_list")
    fun deleteAll()
}