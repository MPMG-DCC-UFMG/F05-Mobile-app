package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.City

@Dao
abstract class CityDAO : BaseDAO<City> {
    @Query("SELECT * FROM ${DatabaseConstants.City.tableName}")
    abstract fun listAllCities(): List<City>

    @Query("SELECT * FROM ${DatabaseConstants.City.tableName}")
    abstract fun listAllCitiesLive(): Flow<List<City>>

    @Query("DELETE FROM ${DatabaseConstants.City.tableName}")
    abstract fun deleteAll()
}