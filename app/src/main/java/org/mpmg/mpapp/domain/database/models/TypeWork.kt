package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TypeWork.tableName)
data class TypeWork(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.TypeWork.flag) val flag: Int,
    @ColumnInfo(name = DatabaseConstants.TypeWork.name) val name: String
): BaseModel