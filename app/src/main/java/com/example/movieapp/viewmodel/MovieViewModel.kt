package com.example.movieapp.viewmodel

import androidx.lifecycle.*
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepo): ViewModel() {

    private val _movieList = MutableLiveData<List<MovieData>>()
    val movieList: LiveData<List<MovieData>> get() = _movieList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllFromDao().distinctUntilChanged().collect {
                _movieList.postValue(it)
            }
        }
    }

    fun fetchTopRated() = liveData {
        emit(ViewState.Loading)
        val response = repo.getTopRated()
        if (response.isSuccessful) emit(ViewState.Success(true))
        else emit(ViewState.Error("Error"))
    }

}