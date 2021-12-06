package com.example.movieapp.repo

import android.app.Application
import com.example.movieapp.repo.remote.MovieService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val service: MovieService, private val dao: MovieDao) {

    //suspend fun getTopRated() = movieService.getTopRated()
}