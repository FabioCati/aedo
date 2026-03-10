package com.fabiocati.aedo.persistence.internal.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.fabiocati.aedo.persistence.internal.dao.MovieDao
import com.fabiocati.aedo.persistence.internal.entity.MovieCategoryEntity
import com.fabiocati.aedo.persistence.internal.entity.MovieEntity

@Database(entities = [MovieEntity::class, MovieCategoryEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
