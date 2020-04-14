package org.mpmg.mpapp.domain.repositories.collect.local

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalPhotoDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalPhotoDataSource {

    override fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectIdLive(collectionId)
    }

    override fun getPhotoByID(photoId: String): Photo {
        return mpDatabase()!!.photoDAO().getPhotoById(photoId)
    }

    override fun insertPhotos(photos: List<Photo>) {
        mpDatabase()!!.photoDAO().insertAll(photos.toTypedArray())
    }

    override fun listPhotosByCollectionID(collectionId: String): List<Photo> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectId(collectionId)
    }
}