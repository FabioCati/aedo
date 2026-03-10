package com.fabiocati.aedo.persistence.internal.db

import androidx.room.*
import com.fabiocati.aedo.persistence.internal.dao.MovieDao
import com.fabiocati.aedo.persistence.internal.entity.*

@Database(
    entities = [
        MovieEntity::class,
        MovieCategoryEntity::class,
        MovieDetailsEntity::class,
        MovieGenreEntity::class,
        MovieCastMemberEntity::class,
        MovieCrewMemberEntity::class,
        MovieTrailerEntity::class,
        MovieLanguageEntity::class
    ],
    version = 1
)
@androidx.room.TypeConverters(AppTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
