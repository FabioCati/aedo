package com.fabiocati.aedo

import android.app.Application
import com.fabiocati.aedo.di.getPlatformModule
import com.fabiocati.aedo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinConfiguration

class AedoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            configuration = KoinConfiguration {
                androidContext(this@AedoApplication)
            },
            platformModule = getPlatformModule()
        )
    }
}