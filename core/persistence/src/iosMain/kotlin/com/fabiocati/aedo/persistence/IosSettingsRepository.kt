package com.fabiocati.aedo.persistence

import platform.Foundation.NSUserDefaults

class IosSettingsRepository : SettingsRepository {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getLiteRTModelPath(): String? {
        return userDefaults.stringForKey(KEY_LITERT_MODEL_PATH)
    }

    override fun setLiteRTModelPath(path: String?) {
        userDefaults.setObject(path, KEY_LITERT_MODEL_PATH)
    }

    override fun getUseLiteRT(): Boolean {
        return userDefaults.boolForKey(KEY_USE_LITERT)
    }

    override fun setUseLiteRT(useLiteRT: Boolean) {
        userDefaults.setBool(useLiteRT, KEY_USE_LITERT)
    }

    companion object {
        private const val KEY_LITERT_MODEL_PATH = "litert_model_path"
        private const val KEY_USE_LITERT = "use_litert"
    }
}
