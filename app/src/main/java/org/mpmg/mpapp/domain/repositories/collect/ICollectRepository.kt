package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo

interface ICollectRepository {

    fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>>

    fun listPhotosByCollectionID(collectionId: String): List<Photo>

    fun getPhotoByID(photoId: String): Photo

    fun insertPhotos(photos: List<Photo>)

    fun insertCollect(collect: Collect, photos: List<Photo>)

    fun getCollect(collectId: String): Collect?

    fun getCollectByPublicWork(publicWorkId: String): Collect?

    fun deletePhotoById(photoId: String)
}