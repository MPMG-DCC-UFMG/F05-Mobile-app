package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.Photo

@Dao
abstract class PhotoDAO : BaseDAO<Photo> {

    @Query("SELECT * FROM ${DatabaseConstants.Photo.tableName}")
    abstract fun listAllPhotos(): List<Photo>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Photo.tableName} " +
                "WHERE ${DatabaseConstants.Photo.idCollect} = :collectId"
    )
    abstract fun listAllPhotosByCollectIdLive(collectId: String): LiveData<List<Photo>>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Photo.tableName} " +
                "WHERE ${DatabaseConstants.Photo.idCollect} = :collectId"
    )
    abstract fun listAllPhotosByCollectId(collectId: String): List<Photo>

    @Query(
        "SELECT * FROM ${DatabaseConstants.Photo.tableName} " +
                "WHERE ${DatabaseConstants.Photo.id} = :photoId"
    )
    abstract fun getPhotoById(photoId: String): Photo?

    @Query(
        "DELETE FROM ${DatabaseConstants.Photo.tableName} " +
                "WHERE ${DatabaseConstants.Photo.id} = :photoId"
    )
    abstract fun deleteById(photoId: String)
}