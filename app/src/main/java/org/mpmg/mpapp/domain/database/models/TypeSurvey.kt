package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TypeSurvey.tableName)
data class TypeSurvey(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.flag) val flag: Int,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.name) val name: String,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.description) val description: String,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.public_work_id) val public_work_id: Int,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.collect_id) val collect_id: Int,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.status) val status: String,
    @ColumnInfo(name = DatabaseConstants.TypeSurvey.user_status) val user_status: Int
) : BaseModel {

    fun getSurveyStatusIds(): List<Int> {
        return status.split(",").map { it.toInt() }
    }
}