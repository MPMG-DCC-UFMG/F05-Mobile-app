package org.mpmg.mpapp.domain.models

import androidx.room.*
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.util.*

@Entity(
    tableName = DatabaseConstants.PublicWork.tableName,
    foreignKeys = [
        ForeignKey(
            entity = TypeWork::class,
            parentColumns = [DatabaseConstants.TypeWork.flag],
            childColumns = [DatabaseConstants.PublicWork.typeWorkFlag],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Collect::class,
            parentColumns = [DatabaseConstants.Collect.id],
            childColumns = [DatabaseConstants.PublicWork.idCollect],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PublicWork(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.PublicWork.id) var id: String = UUID.randomUUID()
        .toString(),
    @ColumnInfo(name = DatabaseConstants.PublicWork.name) var name: String = "",
    @ColumnInfo(name = DatabaseConstants.PublicWork.idCollect) var idCollect: String? = null,
    @ColumnInfo(name = DatabaseConstants.PublicWork.typeWorkFlag) var typeWorkFlag: Int = 1
) : BaseModel {

    fun isValid(): Boolean {
        return name.isNotBlank()
    }
}