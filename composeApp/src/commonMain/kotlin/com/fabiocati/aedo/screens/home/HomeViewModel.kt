package com.fabiocati.aedo.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.fabiocati.aedo.data.movies.MovieRepository
import com.fabiocati.aedo.models.StreamingService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        loadTrendingMovies()
        loadPopularMovies()
        loadNetflixMovies()
        loadDisneyPlusMovies()
        loadAppleTvMovies()
        loadPrimeMovies()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getPopularMovies()
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    popularMovies = movies.value
                )
            }
        }
    }

    private fun loadTrendingMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getTrendingMovies()
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    trendingMovies = movies.value
                )
            }
        }
    }

    private fun loadNetflixMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getStreamingServiceMovies(StreamingService.NETFLIX)
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    netflixMovies = movies.value
                )
            }
        }
    }

    private fun loadDisneyPlusMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getStreamingServiceMovies(StreamingService.DISNEY_PLUS)
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    disneyPlusMovies = movies.value
                )
            }
        }
    }

    private fun loadAppleTvMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getStreamingServiceMovies(StreamingService.APPLE_TV)
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    appleTvMovies = movies.value
                )
            }
        }
    }

    private fun loadPrimeMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getStreamingServiceMovies(StreamingService.AMAZON_PRIME)
            if (movies !is Either.Right) return@launch
            _uiState.update {
                it.copy(
                    primeMovies = movies.value
                )
            }
        }
    }

}