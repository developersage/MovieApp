package com.example.movieapp.repo.remote

import com.example.movieapp.model.TopRated
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "language") language: String?,
        @Query(value = "page") page: Int?
    ): Response<TopRated>

}