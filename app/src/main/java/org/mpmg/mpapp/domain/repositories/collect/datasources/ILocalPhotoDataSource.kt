package org.mpmg.mpapp.domain.repositories.collect.datasources

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Photo

interface ILocalPhotoDataSource {

    fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>>

    fun listPhotosByCollectionID(collectionId: String): List<Photo>

    fun getPhotoByID(photoId: String): Photo

    fun insertPhotos(photos: List<Photo>)

    fun deletePhotoById(photoId: String)

}