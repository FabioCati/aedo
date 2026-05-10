package com.fabiocati.aedo.persistence.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.fabiocati.aedo.persistence.IosSettingsRepository
import com.fabiocati.aedo.persistence.SettingsRepository
import com.fabiocati.aedo.persistence.internal.db.AppDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.cinterop.ExperimentalForeignApi

actual val platformPersistenceModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        val dbFilePath = documentDirectory() + "/aedo.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
    single<SettingsRepository> { IosSettingsRepository() }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
  return requireNotNull(documentDirectory?.path)
}
