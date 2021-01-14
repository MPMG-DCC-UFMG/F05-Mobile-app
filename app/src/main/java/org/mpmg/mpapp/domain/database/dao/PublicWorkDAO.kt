package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

@Dao
abstract class PublicWorkDAO :
    BaseDAO<PublicWork> {

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWork(): List<PublicWork>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWorkLive(): Flow<List<PublicWork>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId"
    )
    @Transaction
    abstract fun getPublicWorkAndAddressById(publicWorkId: String): PublicWorkAndAddress?

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId"
    )
    @Transaction
    abstract fun getPublicWorkById(publicWorkId: String): PublicWork?

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId"
    )
    @Transaction
    abstract fun getPublicWorkAndAddressByIdLive(publicWorkId: String): Flow<PublicWorkAndAddress>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    @Transaction
    abstract fun listAllPublicWorkAndAddress(): List<PublicWorkAndAddress>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    @Transaction
    abstract fun listAllPublicWorkAndAddressLive(): Flow<List<PublicWorkAndAddress>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.toSend} = :status"
    )
    @Transaction
    abstract fun listAllPublicWorkAndAddressByStatus(status: Boolean): List<PublicWorkAndAddress>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.toSend} = :status"
    )
    abstract fun listAllPublicWorkByStatusLive(status: Boolean): Flow<List<PublicWork>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.idCollect} IS NOT NULL " +
                "OR ${DatabaseConstants.PublicWork.toSend} IS 1"
    )
    abstract fun listAllPublicWorkToSendLive(): Flow<List<PublicWork>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.idCollect} IS NOT NULL " +
                "OR ${DatabaseConstants.PublicWork.toSend} IS 1"
    )
    abstract fun listAllPublicWorkToSend(): List<PublicWork>

    @Query(
        "SELECT count(*) FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.idCollect} IS NOT NULL " +
                "OR ${DatabaseConstants.PublicWork.toSend} IS 1"
    )
    abstract fun countPublicWorkToSendLive(): Flow<Int>

    @Query(
        "SELECT count(*) FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.idCollect} IS NOT NULL " +
                "OR ${DatabaseConstants.PublicWork.toSend} IS 1"
    )
    abstract fun countPublicWorkToSend(): Int

    @Query(
        "DELETE FROM ${DatabaseConstants.PublicWork.tableName} " +
                "WHERE ${DatabaseConstants.PublicWork.id} = :publicWorkId "
    )
    abstract fun delete(publicWorkId: String)
}