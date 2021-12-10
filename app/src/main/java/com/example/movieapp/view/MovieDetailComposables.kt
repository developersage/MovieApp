package com.example.movieapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.model.MovieData
import com.example.movieapp.util.ViewState
import com.example.movieapp.viewmodel.MovieDetailViewModel

@ExperimentalCoilApi
@Composable
fun MovieDetailDestination(movieId: Int, padVal: PaddingValues) {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
    movieDetailViewModel.id = movieId
    val state by movieDetailViewModel.viewState.observeAsState()
    state?.let { CreateMovieDetailScreen(state = it, paddingValues = padVal) }
}

@ExperimentalCoilApi
@Composable
fun CreateMovieDetailScreen(
    state: ViewState<MovieData>,
    paddingValues: PaddingValues
) {
    Loader(show = state is ViewState.Loading)
    if (state is ViewState.Success) MovieDetail(movie = state.item, paddingValues = paddingValues)
}

@Composable
fun MovieDetail(movie: MovieData, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = movie.title, modifier = Modifier
                .padding(bottom = 10.dp)
        )
        Image(
            painter = rememberImagePainter(data = movie.posterPath),
            contentDescription = movie.title,
            modifier = Modifier
                .size(400.dp)
                .padding(bottom = 10.dp)
        )
        Text(text = movie.overview, modifier = Modifier.padding(bottom = 10.dp))
        Text(text = movie.releaseDate, modifier = Modifier.padding(bottom = 10.dp))
    }
}