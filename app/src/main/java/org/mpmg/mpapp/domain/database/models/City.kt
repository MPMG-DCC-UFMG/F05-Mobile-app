package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.City.tableName)
data class City(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.City.codigoIbge) val codigoIbge: String,
    @ColumnInfo(name = DatabaseConstants.City.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.City.latitude) val latitude: Double,
    @ColumnInfo(name = DatabaseConstants.City.longitude) val longitude: Double,
    @ColumnInfo(name = DatabaseConstants.City.uf) val uf: String
)