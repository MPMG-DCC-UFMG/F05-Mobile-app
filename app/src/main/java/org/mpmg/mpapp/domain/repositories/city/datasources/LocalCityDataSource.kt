package org.mpmg.mpapp.domain.repositories.city.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalCityDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalCityDataSource {

    override fun insertCity(city: City) {
        mpDatabase()!!.cityDAO().insert(city)
    }

    override fun insertCities(cities: List<City>) {
        mpDatabase()!!.cityDAO().insertAll(cities.toTypedArray())
    }

    override fun listCitiesLive(): LiveData<List<City>> {
        return mpDatabase()!!.cityDAO().listAllCitiesLive()
    }

    override fun deleteCities() {
        mpDatabase()!!.cityDAO().deleteAll()
    }

    override fun listCities(): List<City> {
        return mpDatabase()!!.cityDAO().listAllCities()
    }
}