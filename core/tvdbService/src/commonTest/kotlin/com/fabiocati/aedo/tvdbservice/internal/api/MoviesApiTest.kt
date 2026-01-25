package com.fabiocati.aedo.tvdbservice.internal.api

import arrow.core.Either
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.fail

class MoviesApiTest {


    @Test
    fun getPopularMovies() = runTest {
        val moviesApi = MoviesApiImpl()
        val moviesResult = moviesApi.getPopularMovies(1)
        if (moviesResult.isLeft()) fail()
        moviesResult as Either.Right

        moviesResult.value.forEach { movie ->
            println(movie.logoPath)
        }

    }
}