package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.util.*


@Entity(
    tableName = DatabaseConstants.Address.tableName,
    foreignKeys = [
        ForeignKey(
            entity = PublicWork::class,
            parentColumns = [DatabaseConstants.PublicWork.id],
            childColumns = [DatabaseConstants.Address.idPublicWork],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Address(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.Address.id) var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = DatabaseConstants.Address.idPublicWork) var idPublicWork: String = "",
    @ColumnInfo(name = DatabaseConstants.Address.street) var street: String = "",
    @ColumnInfo(name = DatabaseConstants.Address.neighborhood) var neighborhood: String = "",
    @ColumnInfo(name = DatabaseConstants.Address.number) var number: String = "",
    @ColumnInfo(name = DatabaseConstants.Address.latitude) var latitude: Double = 0.0,
    @ColumnInfo(name = DatabaseConstants.Address.longitude) var longitude: Double = 0.0,
    @ColumnInfo(name = DatabaseConstants.Address.city) var city: String = "",
    @ColumnInfo(name = DatabaseConstants.Address.state) var state: String = "MG",
    @ColumnInfo(name = DatabaseConstants.Address.cep) var cep: String = ""
) {

    override fun toString(): String {
        return "$street, $number - $neighborhood $city - $cep"
    }
}