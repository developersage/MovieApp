package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.ViewState
import com.example.movieapp.util.logMe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<MovieData>>>()
    val viewState: LiveData<ViewState<List<MovieData>>> get() = _viewState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            //fetchTopRated()
            repo.getAllFromDao().distinctUntilChanged().collect {
                _viewState.postValue(ViewState.Success(it))
            }
        }
    }

    fun fetchTopRated() {
        viewModelScope.launch {
            val result = repo.getTopRated()
            if (result.isFailure) {
                _viewState.postValue(ViewState.Error("Failed fetching top rated"))
                "Failed fetching top rated".logMe()
            }
        }
    }

    fun fetchSearch(input: String) {
        viewModelScope.launch {
            val result = repo.searchMovie(input)
            if (result.isFailure) {
                _viewState.postValue(ViewState.Error("Failed fetching search result"))
                "Failed fetching search result".logMe()
            }
        }
    }

}