package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.util.*

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
    @ColumnInfo(name = DatabaseConstants.Collect.id) var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = DatabaseConstants.Collect.idUser) var idUser: String = "",
    @ColumnInfo(name = DatabaseConstants.Collect.idPublicWork) var idPublicWork: String = "",
    @ColumnInfo(name = DatabaseConstants.Collect.date) var date: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = DatabaseConstants.Collect.isSent) var isSent: Boolean = false,
    @ColumnInfo(name = DatabaseConstants.Collect.comments) var comments: String? = null
) : BaseModel