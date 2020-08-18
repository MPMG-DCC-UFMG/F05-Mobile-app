package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.City

@Dao
abstract class CityDAO : BaseDAO<City> {
    @Query("SELECT * FROM ${DatabaseConstants.City.tableName}")
    abstract fun listAllCities(): List<City>

    @Query("SELECT * FROM ${DatabaseConstants.City.tableName}")
    abstract fun listAllCitiesLive(): LiveData<List<City>>
}