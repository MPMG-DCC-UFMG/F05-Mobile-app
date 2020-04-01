package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.Collect

@Dao
abstract class CollectDAO : BaseDAO<Collect> {

    @Query("SELECT * FROM ${DatabaseConstants.Collect.tableName}")
    abstract fun listAllCollects(): List<Collect>
}