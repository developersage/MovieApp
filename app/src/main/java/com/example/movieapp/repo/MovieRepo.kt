package com.example.movieapp.repo

import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.local.MovieDao
import com.example.movieapp.repo.remote.MovieService
import com.example.movieapp.util.logMe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepo @Inject constructor(private val service: MovieService, private val dao: MovieDao) {

    suspend fun getTopRated() = try {
        val topRated = service.getTopRated()
        loadAll(topRated.results)
        Result.success(topRated)
    } catch (ex: Exception) {
        ex.toString().logMe()
        Result.failure(ex.cause ?: Throwable("Something went wrong!"))
    }

    suspend fun searchMovie(input: String) = try {
        val searchResult = service.searchMovie(query = input)
        loadAll(searchResult.results)
        Result.success(searchResult)
    } catch (ex: Exception) {
        ex.toString().logMe()
        Result.failure(ex.cause ?: Throwable("Something went wrong!"))
    }

    fun getAllFromDao() = dao.getAllMovies().flowOn(Dispatchers.IO)

    suspend fun getMovieById(id: Int) = dao.getMovie(id).firstOrNull()

    private suspend fun loadAll(movies: List<Movie>) {
        deleteAll()
        withContext(Dispatchers.IO) {
            dao.insertAll(movies.map { MovieData.convertFromAPI(it) })
        }
    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) { dao.deleteAll() }
    }

}