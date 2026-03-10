package com.fabiocati.aedo.persistence.internal.dao

import androidx.room.*
import com.fabiocati.aedo.persistence.internal.entity.*

@Dao
internal interface MovieDao {
    @Query("""
        SELECT * FROM movies 
        INNER JOIN movie_categories ON movies.id = movie_categories.movieId 
        WHERE movie_categories.category = :category
    """)
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieDetails(movieId: Int): MovieDetailsCompositeEntity?

    @Upsert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Upsert
    suspend fun insertMovie(movie: MovieEntity)

    @Upsert
    suspend fun insertMovieCategories(movieCategories: List<MovieCategoryEntity>)

    @Upsert
    suspend fun insertMovieDetails(details: MovieDetailsEntity)

    @Upsert
    suspend fun insertGenres(genres: List<MovieGenreEntity>)

    @Upsert
    suspend fun insertCast(cast: List<MovieCastMemberEntity>)

    @Upsert
    suspend fun insertCrew(crew: List<MovieCrewMemberEntity>)

    @Upsert
    suspend fun insertTrailers(trailers: List<MovieTrailerEntity>)

    @Upsert
    suspend fun insertLanguages(languages: List<MovieLanguageEntity>)

    @Query("DELETE FROM movie_categories WHERE category = :category")
    suspend fun deleteCategories(category: String)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT DISTINCT movieId FROM movie_categories) AND id NOT IN (SELECT movieId FROM movie_details)")
    suspend fun deleteOrphanedMovies()

    @Transaction
    suspend fun updateMoviesByCategory(category: String, movies: List<MovieEntity>) {
        deleteCategories(category)
        insertMovies(movies)
        insertMovieCategories(movies.map { MovieCategoryEntity(it.id, category) })
        deleteOrphanedMovies()
    }

    @Transaction
    suspend fun insertFullMovieDetails(
        details: MovieDetailsEntity,
        genres: List<MovieGenreEntity>,
        cast: List<MovieCastMemberEntity>,
        crew: List<MovieCrewMemberEntity>,
        trailers: List<MovieTrailerEntity>,
        languages: List<MovieLanguageEntity>
    ) {
        insertMovieDetails(details)
        insertGenres(genres)
        insertCast(cast)
        insertCrew(crew)
        insertTrailers(trailers)
        insertLanguages(languages)
    }
}
