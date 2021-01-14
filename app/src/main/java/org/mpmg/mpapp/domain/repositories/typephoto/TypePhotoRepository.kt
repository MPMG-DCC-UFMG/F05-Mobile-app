package org.mpmg.mpapp.domain.repositories.typephoto

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.LocalTypePhotoDataSource

class TypePhotoRepository(private val localTypePhotoDataSource: LocalTypePhotoDataSource){

    fun insertTypePhoto(typePhoto: TypePhoto) {
        localTypePhotoDataSource.insertTypePhoto(typePhoto)
    }

    fun insertTypePhotos(typePhotos: List<TypePhoto>) {
        localTypePhotoDataSource.insertTypePhotos(typePhotos)
    }

    fun listAllTypePhotos(): List<TypePhoto> {
        return localTypePhotoDataSource.listAllTypePhotos()
    }

    fun listAllTypePhotosLive(): Flow<List<TypePhoto>> {
        return localTypePhotoDataSource.listAllTypePhotosLive()
    }

    fun deleteTypePhotos() {
        localTypePhotoDataSource.deleteTypePhotos()
    }
}