package com.example.movieapp.repo

import android.app.Application
import com.example.movieapp.R
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.local.MovieDao
import com.example.movieapp.repo.remote.MovieService
import com.example.movieapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepo @Inject constructor(private val service: MovieService, private val dao: MovieDao) {

    suspend fun getTopRated() = service.getTopRated(
        R.string.api_key.toString(), null, null
    ).also {
        if (it.isSuccessful && it.body() != null){
            loadAll(it.body()?.results!!)
        }
    }

    fun getAllFromDao() = dao.getAllMovies().flowOn(Dispatchers.IO)

    private suspend fun loadAll(movies: List<Movie>) {
        withContext(Dispatchers.IO){
            dao.insertAll(movies.map {
                MovieData.convertFromAPI(it)
            })
        }
    }

}