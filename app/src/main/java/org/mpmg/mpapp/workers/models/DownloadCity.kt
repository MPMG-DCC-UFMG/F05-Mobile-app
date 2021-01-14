package org.mpmg.mpapp.workers.models

import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.CityRemote
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.repositories.city.CityRepository

class DownloadCity : BaseDownloadInfo<CityRemote>() {

    private val cityRepository: CityRepository by inject()

    override fun resourceId(): Int = R.string.progress_city

    override fun currentVersion(): Int = configRepository.currentCityVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getCityVersion()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveCityVersion(serverVersion)

    override suspend fun loadInfo(): Array<CityRemote> =
        configRepository.loadCities().toTypedArray()

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<CityRemote>()) {
            cityRepository.deleteCities()
            cityRepository.insertCities(list.map { city ->
                city as CityRemote
                city.toCityDB()
            })
            true
        } else {
            false
        }
    }
}