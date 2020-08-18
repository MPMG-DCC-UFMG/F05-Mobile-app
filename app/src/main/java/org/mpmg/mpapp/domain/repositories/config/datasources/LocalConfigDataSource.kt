package org.mpmg.mpapp.domain.repositories.config.datasources

import android.content.Context
import org.mpmg.mpapp.core.Constants
import org.mpmg.mpapp.core.Constants.PREFERENCES_ASSOCIATION_VERSION_KEY
import org.mpmg.mpapp.core.Constants.PREFERENCES_CITY_VERSION_KEY
import org.mpmg.mpapp.core.Constants.PREFERENCES_LOGGED_USER_EMAIL
import org.mpmg.mpapp.core.Constants.PREFERENCES_PUBLIC_WORK_VERSION_KEY
import org.mpmg.mpapp.core.Constants.PREFERENCES_TYPE_PHOTOS_VERSION_KEY
import org.mpmg.mpapp.core.Constants.PREFERENCES_TYPE_WORKS_VERSION_KEY
import org.mpmg.mpapp.core.Constants.PREFERENCES_WORK_STATUS_VERSION_KEY

class LocalConfigDataSource(val applicationContext: Context) : ILocalConfigDataSource {

    private val sharedPreferences = applicationContext.getSharedPreferences(
        Constants.PREFERENCES_MPPAPP_NAME,
        Context.MODE_PRIVATE
    )

    private fun saveVersion(key: String, version: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, version)
            commit()
        }
    }

    override fun setLoggedUserEmail(email: String) {
        with(sharedPreferences.edit()) {
            putString(PREFERENCES_LOGGED_USER_EMAIL, email)
            commit()
        }
    }

    override fun getLoggedUserEmail(): String {
        return sharedPreferences.getString(PREFERENCES_LOGGED_USER_EMAIL, "") ?: ""
    }

    override fun currentTypeWorksVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_TYPE_WORKS_VERSION_KEY, -1)
    }

    override fun currentPublicWorkVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_PUBLIC_WORK_VERSION_KEY, -1)
    }

    override fun currentTypePhotosVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_TYPE_PHOTOS_VERSION_KEY, -1)
    }

    override fun currentAssociationVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_ASSOCIATION_VERSION_KEY, -1)
    }

    override fun currentWorkStatusVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_WORK_STATUS_VERSION_KEY, -1)
    }

    override fun currentCityVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_CITY_VERSION_KEY, -1)
    }

    override fun saveTypePhotosVersion(typePhotosVersion: Int) {
        saveVersion(PREFERENCES_TYPE_PHOTOS_VERSION_KEY, typePhotosVersion)
    }

    override fun saveAssociationsVersion(associationVersion: Int) {
        saveVersion(PREFERENCES_ASSOCIATION_VERSION_KEY, associationVersion)
    }

    override fun saveTypeWorksVersion(typeWorksVersion: Int) {
        saveVersion(PREFERENCES_TYPE_WORKS_VERSION_KEY, typeWorksVersion)
    }

    override fun savePublicWorkVersion(publicWorkVersion: Int) {
        saveVersion(PREFERENCES_PUBLIC_WORK_VERSION_KEY, publicWorkVersion)
    }

    override fun saveWorkStatusVersion(workStatusVersion: Int) {
        saveVersion(PREFERENCES_WORK_STATUS_VERSION_KEY, workStatusVersion)
    }

    override fun saveCityVersion(cityVersion: Int) {
        saveVersion(PREFERENCES_CITY_VERSION_KEY, cityVersion)
    }
}