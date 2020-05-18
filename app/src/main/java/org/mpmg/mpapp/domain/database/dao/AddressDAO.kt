package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.Address

@Dao
abstract class AddressDAO : BaseDAO<Address> {

    @Query("SELECT * FROM ${DatabaseConstants.Address.tableName}")
    abstract fun listAllAddress(): List<Address>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Address.tableName} " +
                "WHERE ${DatabaseConstants.Address.id} = :id"
    )
    abstract fun getAddressById(id: String): Address
}