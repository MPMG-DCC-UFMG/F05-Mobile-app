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

class LocalConfigDataSource(val applicationContext: Context) {

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

    fun setLoggedUserEmail(email: String) {
        with(sharedPreferences.edit()) {
            putString(PREFERENCES_LOGGED_USER_EMAIL, email)
            commit()
        }
    }

    fun getLoggedUserEmail(): String {
        return sharedPreferences.getString(PREFERENCES_LOGGED_USER_EMAIL, "") ?: ""
    }

    fun currentTypeWorksVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_TYPE_WORKS_VERSION_KEY, -1)
    }

    fun currentPublicWorkVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_PUBLIC_WORK_VERSION_KEY, -1)
    }

    fun currentTypePhotosVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_TYPE_PHOTOS_VERSION_KEY, -1)
    }

    fun currentAssociationVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_ASSOCIATION_VERSION_KEY, -1)
    }

    fun currentWorkStatusVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_WORK_STATUS_VERSION_KEY, -1)
    }

    fun currentCityVersion(): Int {
        return sharedPreferences.getInt(PREFERENCES_CITY_VERSION_KEY, -1)
    }

    fun saveTypePhotosVersion(typePhotosVersion: Int) {
        saveVersion(PREFERENCES_TYPE_PHOTOS_VERSION_KEY, typePhotosVersion)
    }

    fun saveAssociationsVersion(associationVersion: Int) {
        saveVersion(PREFERENCES_ASSOCIATION_VERSION_KEY, associationVersion)
    }

    fun saveTypeWorksVersion(typeWorksVersion: Int) {
        saveVersion(PREFERENCES_TYPE_WORKS_VERSION_KEY, typeWorksVersion)
    }

    fun savePublicWorkVersion(publicWorkVersion: Int) {
        saveVersion(PREFERENCES_PUBLIC_WORK_VERSION_KEY, publicWorkVersion)
    }

    fun saveWorkStatusVersion(workStatusVersion: Int) {
        saveVersion(PREFERENCES_WORK_STATUS_VERSION_KEY, workStatusVersion)
    }

    fun saveCityVersion(cityVersion: Int) {
        saveVersion(PREFERENCES_CITY_VERSION_KEY, cityVersion)
    }
}