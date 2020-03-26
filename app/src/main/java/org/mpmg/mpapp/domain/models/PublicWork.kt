package org.mpmg.mpapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.PublicWork.tableName,
    foreignKeys = [
        ForeignKey(
            entity = TypeWork::class,
            parentColumns = [DatabaseConstants.TypeWork.flag],
            childColumns = [DatabaseConstants.PublicWork.typeWorkFlag],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PublicWork(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.PublicWork.id) val id: String,
    @ColumnInfo(name = DatabaseConstants.PublicWork.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.PublicWork.latitude) val latitude: Double,
    @ColumnInfo(name = DatabaseConstants.PublicWork.longitude) val longitude: Double,
    @ColumnInfo(name = DatabaseConstants.PublicWork.lastCollect) val lastCollect: Long? = null,
    @ColumnInfo(name = DatabaseConstants.PublicWork.typeWorkFlag) val typeWorkFlag: Int
)