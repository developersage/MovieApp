package com.example.movieapp.di

import android.content.Context
import android.view.LayoutInflater
import androidx.room.Room
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.repo.local.MovieDao
import com.example.movieapp.repo.local.MovieDatabase
import com.example.movieapp.repo.remote.MovieService
import com.example.movieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_MOVIE_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context
    ): MovieDatabase = Room.databaseBuilder(
            appContext, MovieDatabase::class.java, Constants.DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()

    @Provides
    @Singleton
    fun provideMovieRepo(remote: MovieService, local: MovieDao
    ): MovieRepo = MovieRepo(remote, local)

    @Provides
    fun provideBinding(@ApplicationContext appContext: Context) =
        ActivityMainBinding.inflate(LayoutInflater.from(appContext))
}