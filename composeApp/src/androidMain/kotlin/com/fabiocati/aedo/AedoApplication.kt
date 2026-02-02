package com.fabiocati.aedo

import android.app.Application
import com.fabiocati.aedo.di.initKoin

class AedoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}