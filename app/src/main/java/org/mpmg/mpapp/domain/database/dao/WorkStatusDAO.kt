package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.WorkStatus

@Dao
abstract class WorkStatusDAO : BaseDAO<WorkStatus> {

    @Query(
        "SELECT * FROM ${DatabaseConstants.WorkStatus.tableName} " +
                "WHERE ${DatabaseConstants.WorkStatus.flag} IN (:statusFlags)"
    )
    abstract fun listAllWorkStatusByFlags(statusFlags: List<Int>): List<WorkStatus>
}