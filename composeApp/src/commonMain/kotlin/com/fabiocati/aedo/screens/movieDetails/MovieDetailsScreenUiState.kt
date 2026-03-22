package com.fabiocati.aedo.screens.movieDetails

import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.summarizer.ReviewSummarizer

data class MovieDetailsScreenUiState(
    val movieDetails: MovieDetails? = null,
    val similarMovies: List<Movie> = emptyList(),
    val summarizerStatus: ReviewSummarizer.Status = ReviewSummarizer.Status.NotAvailable,
    val summaryResult: SummaryResult = SummaryResult.Idle
)

sealed interface SummaryResult {
    object Idle : SummaryResult
    object Loading : SummaryResult
    data class Success(val summary: String) : SummaryResult
    data class Error(val message: String) : SummaryResult
}
