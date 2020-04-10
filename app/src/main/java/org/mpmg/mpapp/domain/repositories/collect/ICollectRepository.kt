package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Collect
import org.mpmg.mpapp.domain.models.Photo

interface ICollectRepository {

    fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>>

    fun getPhotoByID(photoId: String): Photo

    fun insertPhotos(photos: List<Photo>)

    fun insertCollect(collect: Collect, photos: List<Photo>)

    fun getCollect(collectId: String): Collect?
}