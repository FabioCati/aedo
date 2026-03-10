package com.fabiocati.aedo.persistence.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.fabiocati.aedo.persistence.MovieLocalDataSource
import com.fabiocati.aedo.persistence.internal.datasource.MovieLocalDataSourceImpl
import com.fabiocati.aedo.persistence.internal.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformPersistenceModule: Module

val persistenceModule = module {
    includes(platformPersistenceModule)
    single<AppDatabase> {
        get<RoomDatabase.Builder<AppDatabase>>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single { get<AppDatabase>().movieDao() }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
}
