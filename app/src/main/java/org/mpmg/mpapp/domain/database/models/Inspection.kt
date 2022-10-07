package org.mpmg.mpapp.domain.database.models

import androidx.room.*
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import java.util.*

@Entity(
    tableName = DatabaseConstants.Inspection.tableName
)
data class Inspection(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.Inspection.name) var name: String = "",
    @ColumnInfo(name = DatabaseConstants.Inspection.flag) var flag: Int = 1,
    @ColumnInfo(name = DatabaseConstants.Inspection.description) var description: String = "",
    @ColumnInfo(name = DatabaseConstants.Inspection.publicWorkId) var publicWorkId: String = "",
    @ColumnInfo(name = DatabaseConstants.Inspection.collectId) var collectId: String = "",
    @ColumnInfo(name = DatabaseConstants.Inspection.status) var status: Int = 1,
    @ColumnInfo(name = DatabaseConstants.Inspection.userStatus) var userStatus: String = ""
) : BaseModel {

    fun isValid(): Boolean {
        return name.isNotBlank()
    }
}