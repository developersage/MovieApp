package com.example.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.util.Constants

@Entity(tableName = "movie_list")
data class MovieData(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double
){
    companion object{
        fun convertFromAPI(movie: Movie) = MovieData(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = Constants.BASE_URL_IMAGE +
                    movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage
        )
    }
}

