package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TypePhoto.tableName)
data class TypePhoto(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.TypePhoto.flag) val flag: Int,
    @ColumnInfo(name = DatabaseConstants.TypePhoto.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.TypePhoto.description) val description: String? = null
) : BaseModel