package com.example.movieapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.model.MovieData
import com.example.movieapp.util.Routes
import com.example.movieapp.util.ViewState
import com.example.movieapp.util.logMe
import com.example.movieapp.viewmodel.MovieViewModel

@ExperimentalCoilApi
@Composable
fun MovieListDestination(navController: NavHostController, padVal: PaddingValues) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val state by movieViewModel.viewState.observeAsState()
    state?.let {
        CreateMovieScreen(
            state = it,
            paddingValues = padVal,
            movieSelected = { movieData ->
                navController.navigate("${Routes.MovieList.route}/${movieData.id}")
            },
            searchQuery = { query -> movieViewModel.fetchSearch(query) },
            fetchTopRated = { movieViewModel.fetchTopRated() }
        )
    }
}


@ExperimentalCoilApi
@Composable
fun CreateMovieScreen(
    state: ViewState<List<MovieData>>,
    paddingValues: PaddingValues,
    movieSelected: (MovieData) -> Unit,
    searchQuery: (String) -> Unit,
    fetchTopRated: () -> Unit
) {
    Loader(show = state is ViewState.Loading)
    if (state is ViewState.Success) MovieList(
        movies = state.item,
        paddingValues = paddingValues,
        movieSelected = movieSelected,
        searchQuery = searchQuery,
        fetchTopRated = fetchTopRated
    )
}

@ExperimentalCoilApi
@Composable
fun MovieList(
    movies: List<MovieData>,
    paddingValues: PaddingValues,
    movieSelected: (MovieData) -> Unit,
    searchQuery: (String) -> Unit,
    fetchTopRated: () -> Unit
) {
    Column() {
        SearchBar(searchQuery, fetchTopRated, paddingValues)
        LazyColumn(
            modifier = Modifier.wrapContentHeight(),
            contentPadding = paddingValues
        ) {
            items(movies) { movie ->
                MovieRow(item = movie, onItemClick = { movieSelected(it) }) }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MovieRow(item: MovieData, onItemClick: (MovieData) -> Unit) {

    Column(
        modifier = Modifier
            .clickable(onClick = { onItemClick(item) })
            .fillMaxWidth()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data = item.posterPath),
            contentDescription = item.title,
            modifier = Modifier.size(400.dp)
        )
        Text(text = item.title)
        Text(text = item.releaseDate)
    }
}

@Composable
fun Loader(show: Boolean) {

}

@Composable
fun SearchBar(
    searchQuery: (String) -> Unit,
    fetchTopRated: () -> Unit,
    paddingValues: PaddingValues) {
    var searchState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .shadow(8.dp)
    ) {
        Row(
            modifier = Modifier.height(50.dp).wrapContentSize()
        ) {
            BasicTextField(
                value = searchState,
                onValueChange = { value: String -> searchState = value },
                modifier = Modifier
                    .width(300.dp)
                    .padding(paddingValues)
                    .shadow(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { searchQuery(searchState) }
                ),
            )
            Button(
                onClick = { fetchTopRated() },
                modifier = Modifier.wrapContentSize()
            ) {
                Text("Top Rated")
            }
        }
    }
}