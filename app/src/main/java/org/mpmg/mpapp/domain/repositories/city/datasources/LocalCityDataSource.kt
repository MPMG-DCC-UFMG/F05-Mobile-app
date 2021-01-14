package org.mpmg.mpapp.domain.repositories.city.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalCityDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertCity(city: City) {
        mpDatabase()!!.cityDAO().insert(city)
    }

    fun insertCities(cities: List<City>) {
        mpDatabase()!!.cityDAO().insertAll(cities.toTypedArray())
    }

    fun listCitiesLive(): Flow<List<City>> {
        return mpDatabase()!!.cityDAO().listAllCitiesLive()
    }

    fun deleteCities() {
        mpDatabase()!!.cityDAO().deleteAll()
    }

    fun listCities(): List<City> {
        return mpDatabase()!!.cityDAO().listAllCities()
    }
}