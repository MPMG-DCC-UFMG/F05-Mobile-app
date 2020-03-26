package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants

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
    @ColumnInfo(name = DatabaseConstants.Photo.id) val id: String,
    @ColumnInfo(name = DatabaseConstants.Photo.idCollect) val idCollect: String,
    @ColumnInfo(name = DatabaseConstants.Photo.filename) val filename: String,
    @ColumnInfo(name = DatabaseConstants.Photo.isSent) val isSent: Boolean = false,
    @ColumnInfo(name = DatabaseConstants.Photo.latitude) val latitude: Double,
    @ColumnInfo(name = DatabaseConstants.Photo.longitude) val longitude: Double,
    @ColumnInfo(name = DatabaseConstants.Photo.comment) val comment: String? = null
)