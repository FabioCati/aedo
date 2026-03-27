package com.fabiocati.aedo.persistence

interface SettingsRepository {
    fun getLiteRTModelPath(): String?
    fun setLiteRTModelPath(path: String?)
}
