package com.fabiocati.aedo.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.fabiocati.aedo.data.movies.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsScreenUiState())
    val uiState: StateFlow<MovieDetailsScreenUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
        loadSimilarMovies()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            val result = movieRepository.getMovieDetails(movieId)
            if (result is Either.Right) {
                _uiState.update { it.copy(movieDetails = result.value) }
            }
        }
    }

    private fun loadSimilarMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getSimilarMovies(movieId)
            if (movies !is Either.Right) return@launch
            _uiState.update { it.copy(similarMovies = movies.value) }
        }
    }

}