package com.example.movieapp.viewmodel

import androidx.lifecycle.*
import com.example.movieapp.model.MovieData
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<MovieData>>(ViewState.Loading)
    val viewState: LiveData<ViewState<MovieData>> get() = _viewState

    var id: Int = -1
        set(value) {
            if (field != value) {
                viewModelScope.launch(Dispatchers.IO) {
                    val state = try {
                        ViewState.Success(repo.getMovieById(value)
                            ?: throw IllegalStateException("No movies found with id $value.")
                        )
                    } catch (ex: Exception) {
                        ViewState.Error("Failed getting details")
                    }
                    _viewState.postValue(state)
                    field = value
                }
            }
        }
}