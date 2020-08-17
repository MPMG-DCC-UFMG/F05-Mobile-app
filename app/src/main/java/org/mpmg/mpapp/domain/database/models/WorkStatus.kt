package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.WorkStatus.tableName)
data class WorkStatus(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.WorkStatus.flag) val flag: Int,
    @ColumnInfo(name = DatabaseConstants.WorkStatus.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.WorkStatus.description) val description: String?
) : BaseModel