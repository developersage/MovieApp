package com.example.movieapp.viewmodel

import androidx.lifecycle.*
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepo): ViewModel() {

    private val _movieList = MutableLiveData<List<MovieData>>()
    val movieList: LiveData<List<MovieData>> get() = _movieList

    private val _viewState = MutableLiveData<ViewState<Any>>()
    val viewState: LiveData<ViewState<Any>> get() = _viewState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getAllFromDao().distinctUntilChanged().collect {
                _movieList.postValue(it)
            }
        }
    }

    fun fetchTopRated() {
        viewModelScope.launch {
            val response = repo.getTopRated()
            _viewState.postValue(
                if (response.isSuccessful) ViewState.Success(true)
                else ViewState.Error("Error")
            )

        }
    }

}