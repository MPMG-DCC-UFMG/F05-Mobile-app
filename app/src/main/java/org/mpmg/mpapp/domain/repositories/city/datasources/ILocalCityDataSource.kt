package org.mpmg.mpapp.domain.repositories.city.datasources

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.City

interface ILocalCityDataSource {

    fun insertCity(city: City)

    fun insertCities(cities: List<City>)

    fun listCitiesLive(): LiveData<List<City>>
}