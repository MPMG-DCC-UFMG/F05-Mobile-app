package org.mpmg.mpapp.domain.repositories.typephoto.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypePhotoDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalTypePhotoDataSource {

    override fun insertTypePhoto(typePhoto: TypePhoto) {
        mpDatabase()!!.typePhotoDAO().insert(typePhoto)
    }

    override fun insertTypePhotos(typePhotos: List<TypePhoto>) {
        mpDatabase()!!.typePhotoDAO().insertAll(typePhotos.toTypedArray())
    }

    override fun listAllTypePhotos(): List<TypePhoto> {
        return mpDatabase()!!.typePhotoDAO().listAllTypePhotos()
    }

    override fun listAllTypePhotosLive(): LiveData<List<TypePhoto>> {
        return mpDatabase()!!.typePhotoDAO().listAllTypePhotosLive()
    }
}