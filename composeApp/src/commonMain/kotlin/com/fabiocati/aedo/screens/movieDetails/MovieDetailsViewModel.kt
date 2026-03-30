package com.fabiocati.aedo.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.fabiocati.aedo.data.movies.MovieRepository
import com.fabiocati.aedo.summarizer.ReviewSummarizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    private val reviewSummarizer: ReviewSummarizer,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsScreenUiState())
    val uiState: StateFlow<MovieDetailsScreenUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
        loadSimilarMovies()
        observeSummarizerStatus()
        viewModelScope.launch(Dispatchers.IO) {
            reviewSummarizer.conversation.collect { chat ->
                if(chat.isEmpty()) return@collect
                _uiState.update { it.copy(summaryResult = SummaryResult.Success(chat)) }
            }
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRepository.getMovieDetails(movieId)
            if (result is Either.Right) {
                _uiState.update { it.copy(movieDetails = result.value) }
            }
        }
    }

    private fun loadSimilarMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = movieRepository.getSimilarMovies(movieId)
            if (movies !is Either.Right) return@launch
            _uiState.update { it.copy(similarMovies = movies.value) }
        }
    }

    private fun observeSummarizerStatus() {
        viewModelScope.launch {
            reviewSummarizer.status.collectLatest { status ->
                _uiState.update { it.copy(summarizerStatus = status) }
            }
        }
    }

    fun downloadSummarizerModel() {
        reviewSummarizer.downloadModel()
    }

    fun summarizeReviews() {
        val reviews = uiState.value.movieDetails?.reviews ?: return
        if (reviews.isEmpty()) return

        _uiState.update { it.copy(summaryResult = SummaryResult.Loading) }

        viewModelScope.launch(Dispatchers.IO) {
            val summary = reviewSummarizer.summarize(reviews)
            _uiState.update { it.copy(summaryResult = SummaryResult.Success(summary)) }
        }
    }

    fun dismissSummary() {
        _uiState.update { it.copy(summaryResult = SummaryResult.Idle) }
    }

    override fun onCleared() {
        super.onCleared()
        reviewSummarizer.close()
    }
}
