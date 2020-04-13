package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.sql.Timestamp
import java.util.*

@Entity(
    tableName = DatabaseConstants.Photo.tableName,
    foreignKeys = [
        ForeignKey(
            entity = Collect::class,
            parentColumns = [DatabaseConstants.Collect.id],
            childColumns = [DatabaseConstants.Photo.idCollect],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.Photo.id) var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = DatabaseConstants.Photo.idCollect) var idCollect: String = "",
    @ColumnInfo(name = DatabaseConstants.Photo.filepath) var filepath: String? = null,
    @ColumnInfo(name = DatabaseConstants.Photo.type) var type: String? = null,
    @ColumnInfo(name = DatabaseConstants.Photo.isSent) var isSent: Boolean = false,
    @ColumnInfo(name = DatabaseConstants.Photo.latitude) var latitude: Double = 0.0,
    @ColumnInfo(name = DatabaseConstants.Photo.longitude) var longitude: Double = 0.0,
    @ColumnInfo(name = DatabaseConstants.Photo.timestamp) var timestamp: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = DatabaseConstants.Photo.comment) var comment: String? = null
) : BaseModel