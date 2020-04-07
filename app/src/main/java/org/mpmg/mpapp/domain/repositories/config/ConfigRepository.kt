package org.mpmg.mpapp.domain.repositories.config

import android.content.Context
import android.preference.PreferenceManager
import androidx.preference.Preference
import org.mpmg.mpapp.core.Constants
import org.mpmg.mpapp.domain.apis.MPApi
import org.mpmg.mpapp.domain.models.TypeWork

class ConfigRepository(val mpApi: MPApi, val applicationContext: Context) : IConfigRepository {

    override fun loadListTypeWorks(): List<TypeWork> {
        return mpApi.loadTypeWorkAPI()
    }

    override fun getServerConfigFilesVersion(): Int {
        return mpApi.getConfigFilesVersion()
    }

    override fun saveConfigFilesVersion(configVersion: Int) {
        val sharedPreferences = applicationContext.getSharedPreferences(
            Constants.PREFERENCES_MPPAPP_NAME,
            Context.MODE_PRIVATE
        )
        with(sharedPreferences.edit()) {
            putInt(Constants.PREFERENCES_CONFIG_FILES_VERSION_KEY, configVersion)
            commit()
        }
    }

    override fun currentFilesVersion(): Int {
        val sharedPreferences = applicationContext.getSharedPreferences(
            Constants.PREFERENCES_MPPAPP_NAME,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getInt(Constants.PREFERENCES_CONFIG_FILES_VERSION_KEY, 0)
    }
}