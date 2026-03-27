package com.fabiocati.aedo.persistence

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AndroidSettingsRepository(
    context: Context
) : SettingsRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("aedo_settings", Context.MODE_PRIVATE)

    override fun getLiteRTModelPath(): String? {
        return sharedPreferences.getString(KEY_LITERT_MODEL_PATH, null)
    }

    override fun setLiteRTModelPath(path: String?) {
        sharedPreferences.edit { putString(KEY_LITERT_MODEL_PATH, path) }
    }

    companion object {
        private const val KEY_LITERT_MODEL_PATH = "litert_model_path"
    }
}
