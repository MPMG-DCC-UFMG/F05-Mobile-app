package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.Collect

@Dao
abstract class CollectDAO : BaseDAO<Collect> {

    @Query("SELECT * FROM ${DatabaseConstants.Collect.tableName}")
    abstract fun listAllCollects(): List<Collect>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Collect.tableName} " +
                "WHERE ${DatabaseConstants.Collect.id} = :collectId"
    )
    abstract fun getCollectById(collectId: String): Collect?

    @Query(
        "SELECT * FROM ${DatabaseConstants.Collect.tableName} " +
                "WHERE ${DatabaseConstants.Collect.idPublicWork} = :publicId " +
                "AND ${DatabaseConstants.Collect.isSent} = :status"
    )
    abstract fun getCollectByPublicIdAndStatus(publicId: String, status: Boolean): Collect?
}