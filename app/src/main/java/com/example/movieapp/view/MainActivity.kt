package com.example.movieapp.view

import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.movieapp.util.MovieDetailNavKey
import com.example.movieapp.util.Routes
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalAnimationApi
    @ExperimentalCoilApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {

        // Splash Screen Set Up Before setting Content View
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainLayout()
            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalCoilApi
    @ExperimentalMaterial3Api
    @Composable
    fun MainLayout() {
        Scaffold(
            topBar = { SetUpTopBar() },
        ) { padVal ->
            Navigation(padVal = padVal)
        }
    }

    @ExperimentalCoilApi
    @ExperimentalAnimationApi
    @Composable
    fun Navigation(padVal: PaddingValues) {

        val navController = rememberAnimatedNavController()
        AnimatedNavHost(navController, startDestination = Routes.MovieList.route) {

            composable(
                Routes.MovieList.route,
                enterTransition = {
                    this.slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Right
                    )
                },
                exitTransition = {
                    this.slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Left
                    )
                },
            ) { MovieListDestination(navController, padVal) }

            composable(
                Routes.MovieDetail.route,
                enterTransition = {
                    this.slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    this.slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Right
                    )
                },
                arguments = listOf(navArgument(MovieDetailNavKey.MOVIE_ID) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt(MovieDetailNavKey.MOVIE_ID)?.let {
                    MovieDetailDestination(it, padVal)
                }
            }
        }

    }

    @Composable
    fun SetUpTopBar() {
        MediumTopAppBar(
            title = { Text("Movie App") },
            colors = mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        )
    }


}

