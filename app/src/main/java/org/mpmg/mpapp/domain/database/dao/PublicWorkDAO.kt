package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress

@Dao
abstract class PublicWorkDAO :
    BaseDAO<PublicWork> {

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWork(): List<PublicWork>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWorkLive(): LiveData<List<PublicWork>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId"
    )
    @Transaction
    abstract fun getPublicWorkAndAddressById(publicWorkId: String): PublicWorkAndAdress?

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId"
    )
    @Transaction
    abstract fun getPublicWorkAndAddressByIdLive(publicWorkId: String): LiveData<PublicWorkAndAdress>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    @Transaction
    abstract fun listAllPublicWorkAndAddress(): List<PublicWorkAndAdress>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    @Transaction
    abstract fun listAllPublicWorkAndAddressLive(): LiveData<List<PublicWorkAndAdress>>
}