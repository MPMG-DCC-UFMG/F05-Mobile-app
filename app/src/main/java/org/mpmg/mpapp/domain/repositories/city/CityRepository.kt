package org.mpmg.mpapp.domain.repositories.city

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.domain.repositories.city.datasources.LocalCityDataSource

class CityRepository(private val localCityDataSource: LocalCityDataSource) {

    fun insertCity(city: City) {
        localCityDataSource.insertCity(city)
    }

    fun insertCities(cities: List<City>) {
        localCityDataSource.insertCities(cities)
    }

    fun listCitiesLive(): Flow<List<City>> {
        return localCityDataSource.listCitiesLive()
    }

    fun deleteCities() {
        localCityDataSource.deleteCities()
    }

    fun listCities(): List<City> {
        return localCityDataSource.listCities()
    }
}