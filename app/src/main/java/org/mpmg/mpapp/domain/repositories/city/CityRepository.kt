package org.mpmg.mpapp.domain.repositories.city

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.domain.repositories.city.datasources.ILocalCityDataSource

class CityRepository(private val localCityDataSource: ILocalCityDataSource) : ICityRepository {

    override fun insertCity(city: City) {
        localCityDataSource.insertCity(city)
    }

    override fun insertCities(cities: List<City>) {
        localCityDataSource.insertCities(cities)
    }

    override fun listCitiesLive(): LiveData<List<City>> {
        return localCityDataSource.listCitiesLive()
    }

    override fun deleteCities() {
        localCityDataSource.deleteCities()
    }
}