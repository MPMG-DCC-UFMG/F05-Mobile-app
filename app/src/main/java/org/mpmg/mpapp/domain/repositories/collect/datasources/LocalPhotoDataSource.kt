package org.mpmg.mpapp.domain.repositories.collect.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource
import java.io.File

class LocalPhotoDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun listPhotosByCollectionIDLive(collectionId: String): Flow<List<Photo>> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectIdLive(collectionId)
    }

    fun getPhotoByID(photoId: String): Photo? {
        return mpDatabase()!!.photoDAO().getPhotoById(photoId)
    }

    fun insertPhotos(photos: List<Photo>) {
        mpDatabase()!!.photoDAO().insertAll(photos.toTypedArray())
    }

    fun listPhotosByCollectionID(collectionId: String): List<Photo> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectId(collectionId)
    }

    fun deletePhotoById(photoId: String) {
        val photo = mpDatabase()!!.photoDAO().getPhotoById(photoId)
        photo?.let {
            deletePhotoFile(it.filepath)
            mpDatabase()!!.photoDAO().deleteById(photoId)
        }

    }

    private fun deletePhotoFile(filePath: String?) {
        filePath ?: return

        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
    }
}