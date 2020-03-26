package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.Photo

@Dao
abstract class PhotoDAO : BaseDAO<Photo> {

    @Query("SELECT * FROM ${DatabaseConstants.Photo.tableName}")
    abstract fun listAllPhotos(): List<Photo>
}