package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.AssociationTWTP

@Dao
abstract class AssociationDAO : BaseDAO<AssociationTWTP> {

    @Query("DELETE FROM ${DatabaseConstants.AssociationTWTP.tableName}")
    abstract fun deleteAll()
}