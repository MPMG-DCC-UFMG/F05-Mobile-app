package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.User.tableName)
data class User(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.User.email) val email: String,
    @ColumnInfo(name = DatabaseConstants.User.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.User.cpf) val cpf: String? = null
) : BaseModel