package org.mpmg.mpapp.domain.repositories.config

import android.content.Context
import org.mpmg.mpapp.core.Constants
import org.mpmg.mpapp.domain.network.MPApi
import org.mpmg.mpapp.domain.models.TypeWork

class ConfigRepository(val mpApi: MPApi, val applicationContext: Context) : IConfigRepository {

    private val sharedPreferences = applicationContext.getSharedPreferences(
        Constants.PREFERENCES_MPPAPP_NAME,
        Context.MODE_PRIVATE
    )

    override fun loadListTypeWorks(): List<TypeWork> {
        return mpApi.loadTypeWorkAPI()
    }

    override fun getServerConfigFilesVersion(): Int {
        return mpApi.getConfigFilesVersion()
    }

    override fun saveConfigFilesVersion(configVersion: Int) {
        with(sharedPreferences.edit()) {
            putInt(Constants.PREFERENCES_CONFIG_FILES_VERSION_KEY, configVersion)
            commit()
        }
    }

    override fun currentFilesVersion(): Int {
        return sharedPreferences.getInt(Constants.PREFERENCES_CONFIG_FILES_VERSION_KEY, 0)
    }

    override fun setLoggedUserEmail(email: String) {
        with(sharedPreferences.edit()) {
            putString(Constants.PREFERENCES_LOGGED_USER_EMAIL, email)
            commit()
        }
    }

    override fun getLoggedUserEmail(): String {
        return sharedPreferences.getString(Constants.PREFERENCES_LOGGED_USER_EMAIL, "") ?: ""
    }
}