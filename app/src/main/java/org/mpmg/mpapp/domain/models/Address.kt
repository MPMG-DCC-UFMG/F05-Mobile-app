package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants


@Entity(tableName = DatabaseConstants.Address.tableName)
data class Address(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.Address.id) val id: String,
    @ColumnInfo(name = DatabaseConstants.Address.street) val street: String,
    @ColumnInfo(name = DatabaseConstants.Address.neighborhood) val neighborhood: String,
    @ColumnInfo(name = DatabaseConstants.Address.number) val number: String,
    @ColumnInfo(name = DatabaseConstants.Address.latitude) val latitude: Double,
    @ColumnInfo(name = DatabaseConstants.Address.longitude) val longitude: Double,
    @ColumnInfo(name = DatabaseConstants.Address.city) val city: String,
    @ColumnInfo(name = DatabaseConstants.Address.state) val state: String,
    @ColumnInfo(name = DatabaseConstants.Address.cep) val cep: String
) {

    override fun toString(): String {
        return "$street, $number - $neighborhood $city - $cep"
    }
}