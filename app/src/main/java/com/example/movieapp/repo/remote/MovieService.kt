package com.example.movieapp.repo.remote

import com.example.movieapp.model.RequestResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    companion object {
        private const val API_KEY = "8015b18728cc0dc36dcc87779e3697a9"
    }

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query(value = "api_key") apiKey: String = API_KEY,
        @Query(value = "language") language: String? = null,
        @Query(value = "page") page: Int? = null
    ): RequestResult

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(value = "api_key") apiKey: String = API_KEY,
        @Query(value = "query") query: String,
        @Query(value = "language") language: String? = null,
        @Query(value = "page") page: Int? = null
    ): RequestResult
}