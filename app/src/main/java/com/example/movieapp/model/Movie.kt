package com.example.movieapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,

): Parcelable

@JsonClass(generateAdapter = true)
data class RequestResult(
    val page: Int,
    val results: List<Movie>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)


/*
* {
    "page": 1,
    "results": [
        {
            "adult": false,
            "backdrop_path": "/5hNcsnMkwU2LknLoru73c76el3z.jpg",
            "genre_ids": [
                35,
                18,
                10749
            ],
            "id": 19404,
            "original_language": "hi",
            "original_title": "दिलवाले दुल्हनिया ले जायेंगे",
            "overview": "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
            "popularity": 26.248,
            "poster_path": "/2CAL2433ZeIihfX1Hb2139CX0pW.jpg",
            "release_date": "1995-10-20",
            "title": "Dilwale Dulhania Le Jayenge",
            "video": false,
            "vote_average": 8.7,
            "vote_count": 3264
        }
    ],
    "total_pages": 470,
    "total_results": 9389
}
* */