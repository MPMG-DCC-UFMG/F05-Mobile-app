package org.mpmg.mpapp.domain.repositories.config.datasources

import android.content.Context
import org.mpmg.mpapp.core.Constants

class LocalConfigDataSource(val applicationContext: Context) : ILocalConfigDataSource {

    private val sharedPreferences = applicationContext.getSharedPreferences(
        Constants.PREFERENCES_MPPAPP_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveTypeWorksVersion(typeWorksVersion: Int) {
        with(sharedPreferences.edit()) {
            putInt(Constants.PREFERENCES_TYPE_WORKS_VERSION_KEY, typeWorksVersion)
            commit()
        }
    }

    override fun currentTypeWorksVersion(): Int {
        return sharedPreferences.getInt(Constants.PREFERENCES_TYPE_WORKS_VERSION_KEY, -1)
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

    override fun savePublicWorkVersion(publicWorkVersion: Int) {
        with(sharedPreferences.edit()) {
            putInt(Constants.PREFERENCES_PUBLIC_WORK_VERSION_KEY, publicWorkVersion)
            commit()
        }
    }

    override fun currentPublicWorkVersion(): Int {
        return sharedPreferences.getInt(Constants.PREFERENCES_PUBLIC_WORK_VERSION_KEY, -1)
    }
}