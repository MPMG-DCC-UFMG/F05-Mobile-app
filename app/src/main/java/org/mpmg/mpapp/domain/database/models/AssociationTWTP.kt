package org.mpmg.mpapp.domain.database.models

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import org.mpmg.mpapp.domain.database.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.AssociationTWTP.tableName,
    primaryKeys = [DatabaseConstants.AssociationTWTP.typePhotoFlag, DatabaseConstants.AssociationTWTP.typeWorkFlag]
)
data class AssociationTWTP(
    @ColumnInfo(name = DatabaseConstants.AssociationTWTP.typePhotoFlag) val typePhotoFlag: Int,
    @ColumnInfo(name = DatabaseConstants.AssociationTWTP.typeWorkFlag) val typeWorkFlag: Int
)