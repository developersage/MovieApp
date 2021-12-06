package com.example.movieapp.model

import com.example.movieapp.repo.remote.MovieService
import com.example.movieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_MOVIE_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieDatabase():

}