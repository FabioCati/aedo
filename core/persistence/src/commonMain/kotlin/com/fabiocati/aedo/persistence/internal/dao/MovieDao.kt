package com.fabiocati.aedo.persistence.internal.dao

import androidx.room.*
import com.fabiocati.aedo.persistence.internal.entity.MovieCategoryEntity
import com.fabiocati.aedo.persistence.internal.entity.MovieEntity

@Dao
internal interface MovieDao {
    @Query("""
        SELECT * FROM movies 
        INNER JOIN movie_categories ON movies.id = movie_categories.movieId 
        WHERE movie_categories.category = :category
    """)
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    @Upsert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Upsert
    suspend fun insertMovieCategories(movieCategories: List<MovieCategoryEntity>)

    @Query("DELETE FROM movie_categories WHERE category = :category")
    suspend fun deleteCategories(category: String)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT DISTINCT movieId FROM movie_categories)")
    suspend fun deleteOrphanedMovies()

    @Transaction
    suspend fun updateMoviesByCategory(category: String, movies: List<MovieEntity>) {
        deleteCategories(category)
        insertMovies(movies)
        insertMovieCategories(movies.map { MovieCategoryEntity(it.id, category) })
        deleteOrphanedMovies()
    }
}
