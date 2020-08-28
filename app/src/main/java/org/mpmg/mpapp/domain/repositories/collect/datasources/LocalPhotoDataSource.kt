package org.mpmg.mpapp.domain.repositories.collect.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource
import java.io.File

class LocalPhotoDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalPhotoDataSource {

    override fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectIdLive(collectionId)
    }

    override fun getPhotoByID(photoId: String): Photo? {
        return mpDatabase()!!.photoDAO().getPhotoById(photoId)
    }

    override fun insertPhotos(photos: List<Photo>) {
        mpDatabase()!!.photoDAO().insertAll(photos.toTypedArray())
    }

    override fun listPhotosByCollectionID(collectionId: String): List<Photo> {
        return mpDatabase()!!.photoDAO().listAllPhotosByCollectId(collectionId)
    }

    override fun deletePhotoById(photoId: String) {
        val photo = mpDatabase()!!.photoDAO().getPhotoById(photoId)
        photo?.let {
            deletePhotoFile(it.filepath)
            mpDatabase()!!.photoDAO().deleteById(photoId)
        }

    }

    private fun deletePhotoFile(filePath: String?) {
        filePath?:return

        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
    }
}