package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.PublicWork

@Dao
abstract class PublicWorkDAO :
    BaseDAO<PublicWork> {

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWork(): List<PublicWork>

    @Query("SELECT * FROM ${DatabaseConstants.PublicWork.tableName}")
    abstract fun listAllPublicWorkLive(): LiveData<List<PublicWork>>
}