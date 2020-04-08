package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.Collect.tableName,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [DatabaseConstants.User.email],
            childColumns = [DatabaseConstants.Collect.idUser],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PublicWork::class,
            parentColumns = [DatabaseConstants.PublicWork.id],
            childColumns = [DatabaseConstants.Collect.idPublicWork],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Collect(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.Collect.id) val id: String,
    @ColumnInfo(name = DatabaseConstants.Collect.idUser) val idUser: String,
    @ColumnInfo(name = DatabaseConstants.Collect.idPublicWork) val idPublicWork: String,
    @ColumnInfo(name = DatabaseConstants.Collect.date) val date: Long,
    @ColumnInfo(name = DatabaseConstants.Collect.isSent) val isSent: Boolean = false,
    @ColumnInfo(name = DatabaseConstants.Collect.comments) val comments: String? = null
) : BaseModel