package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.User.tableName,
    indices = [Index(value = [DatabaseConstants.User.email], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.User.id) val id: Int? = null,
    @ColumnInfo(name = DatabaseConstants.User.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.User.email) val email: String,
    @ColumnInfo(name = DatabaseConstants.User.cpf) val cpf: String? = null
)