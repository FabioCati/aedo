package com.fabiocati.aedo.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            val movieDetails = movieRepository.getMovieDetails(movieId)
            _uiState.update { it.copy(movieDetails = movieDetails) }
        }
    }

}