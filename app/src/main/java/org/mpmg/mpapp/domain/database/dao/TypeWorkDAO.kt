package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.TypeWork

@Dao
abstract class TypeWorkDAO : BaseDAO<TypeWork> {

    @Query("SELECT * FROM ${DatabaseConstants.TypeWork.tableName}")
    abstract fun listAllTypeWork(): List<TypeWork>

    @Query("SELECT * FROM ${DatabaseConstants.TypeWork.tableName}")
    abstract fun listAllTypeWorkLive(): Flow<List<TypeWork>>

    @Query("SELECT * FROM ${DatabaseConstants.TypeWork.tableName} WHERE ${DatabaseConstants.TypeWork.flag} = :flag")
    abstract fun getTypeWorkByFlag(flag: Int): TypeWork?

    @Query("DELETE FROM ${DatabaseConstants.TypeWork.tableName}")
    abstract fun deleteAll()
}