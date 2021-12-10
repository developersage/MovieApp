package com.example.movieapp.util

object MovieDetailNavKey {
    const val MOVIE_ID = "movie_id"
}

sealed class Routes(val route: String) {
    companion object {
        private const val MOVIE_LIST = "MovieList"
    }

    object MovieList : Routes(MOVIE_LIST)
    object MovieDetail : Routes("$MOVIE_LIST/{${MovieDetailNavKey.MOVIE_ID}}")
}
