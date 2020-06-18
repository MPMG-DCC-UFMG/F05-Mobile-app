package org.mpmg.mpapp.domain.repositories.typephoto

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.ILocalTypePhotoDataSource

class TypePhotoRepository(private val localTypePhotoDataSource: ILocalTypePhotoDataSource) :
    ITypePhotoRepository {

    override fun insertTypePhoto(typePhoto: TypePhoto) {
        localTypePhotoDataSource.insertTypePhoto(typePhoto)
    }

    override fun insertTypePhotos(typePhotos: List<TypePhoto>) {
        localTypePhotoDataSource.insertTypePhotos(typePhotos)
    }

    override fun listAllTypePhotos(): List<TypePhoto> {
        return localTypePhotoDataSource.listAllTypePhotos()
    }

    override fun listAllTypePhotosLive(): LiveData<List<TypePhoto>> {
        return localTypePhotoDataSource.listAllTypePhotosLive()
    }
}