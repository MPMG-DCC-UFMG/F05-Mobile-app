package org.mpmg.mpapp.domain.repositories.collect.local

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Photo

interface ILocalPhotoDataSource {

    fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>>

    fun listPhotosByCollectionID(collectionId: String): List<Photo>

    fun getPhotoByID(photoId: String): Photo

    fun insertPhotos(photos: List<Photo>)
}