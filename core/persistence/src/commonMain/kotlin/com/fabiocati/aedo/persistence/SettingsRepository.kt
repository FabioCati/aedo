package com.fabiocati.aedo.persistence

interface SettingsRepository {
    fun getLiteRTModelPath(): String?
    fun setLiteRTModelPath(path: String?)
    fun getUseLiteRT(): Boolean
    fun setUseLiteRT(useLiteRT: Boolean)
}
