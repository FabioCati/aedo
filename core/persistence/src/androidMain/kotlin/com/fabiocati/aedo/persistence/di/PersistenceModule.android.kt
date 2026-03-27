package com.fabiocati.aedo.persistence.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.fabiocati.aedo.persistence.AndroidSettingsRepository
import com.fabiocati.aedo.persistence.SettingsRepository
import com.fabiocati.aedo.persistence.internal.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformPersistenceModule: Module = module {
    single<SettingsRepository> { AndroidSettingsRepository(androidContext()) }
    single<RoomDatabase.Builder<AppDatabase>> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("aedo.db")
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    }
}
