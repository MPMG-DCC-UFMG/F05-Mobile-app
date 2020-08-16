package org.mpmg.mpapp.domain.database.models

import androidx.room.*
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.util.*

@Entity(
    tableName = DatabaseConstants.PublicWork.tableName
)
data class PublicWork(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.PublicWork.id) var id: String = UUID.randomUUID()
        .toString(),
    @ColumnInfo(name = DatabaseConstants.PublicWork.name) var name: String = "",
    @ColumnInfo(name = DatabaseConstants.PublicWork.idCollect) var idCollect: String? = null,
    @ColumnInfo(name = DatabaseConstants.PublicWork.typeWorkFlag) var typeWorkFlag: Int = 1,
    @ColumnInfo(name = DatabaseConstants.PublicWork.toSend) var toSend: Boolean = false,
    @ColumnInfo(name = DatabaseConstants.PublicWork.userStatusFlag) var userStatusFlag: Int? = null
) : BaseModel {

    fun isValid(): Boolean {
        return name.isNotBlank()
    }
}