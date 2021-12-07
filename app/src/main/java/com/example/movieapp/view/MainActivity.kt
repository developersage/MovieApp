package com.example.movieapp.view

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.model.MovieData
import com.example.movieapp.util.ViewState
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var app: Application

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {

        // Splash Screen Set Up Before setting Content View
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainLayout(viewModel = viewModel)
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    fun MainLayout(viewModel: MovieViewModel){
        Scaffold { pval ->
            val movies by viewModel.movieList.observeAsState()

            if (!movies.isNullOrEmpty()){
                MovieList(movies = movies!!, paddingValues = pval)
            }
            SetUpButtonFetchTopRated()
        }
    }

    @Composable
    fun SetUpButtonFetchTopRated() {
        val fetch by viewModel.viewState.observeAsState()
        Button(onClick = {
            viewModel.fetchTopRated()
            when (fetch){
                is ViewState.Loading -> {}
                is ViewState.Error -> {}
                is ViewState.Success -> {
                    Toast.makeText(application , "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Fetch All")
        }
    }

    @Composable
    fun MovieList(movies: List<MovieData>, paddingValues: PaddingValues) {
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(movies) { movie ->
                ItemRow(movie)
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun ItemRow(item: MovieData) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data = item.posterPath),
                contentDescription = item.title,
                modifier = Modifier.size(200.dp)
            )
            Text(text = item.overview)
            Text(text = item.releaseDate)
        }
    }

}

