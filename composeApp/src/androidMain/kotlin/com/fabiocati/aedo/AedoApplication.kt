package com.fabiocati.aedo

import android.app.Application
import com.fabiocati.aedo.tvdbservice.di.tvdbServiceModules
import org.koin.core.context.startKoin

class AedoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                tvdbServiceModules
            )
        }
    }
}