package org.mpmg.mpapp.domain.repositories.typephoto.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypePhotoDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertTypePhoto(typePhoto: TypePhoto) {
        mpDatabase()!!.typePhotoDAO().insert(typePhoto)
    }

    fun insertTypePhotos(typePhotos: List<TypePhoto>) {
        mpDatabase()!!.typePhotoDAO().insertAll(typePhotos.toTypedArray())
    }

    fun listAllTypePhotos(): List<TypePhoto> {
        return mpDatabase()!!.typePhotoDAO().listAllTypePhotos()
    }

    fun listAllTypePhotosLive(): Flow<List<TypePhoto>> {
        return mpDatabase()!!.typePhotoDAO().listAllTypePhotosLive()
    }

    fun deleteTypePhotos() {
        mpDatabase()!!.typePhotoDAO().deleteAll()
    }
}