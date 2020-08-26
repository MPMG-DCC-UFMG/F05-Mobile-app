package org.mpmg.mpapp.domain.repositories.typephoto.datasources

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypePhoto

interface ILocalTypePhotoDataSource {

    fun insertTypePhoto(typePhoto: TypePhoto)

    fun insertTypePhotos(typePhotos: List<TypePhoto>)

    fun listAllTypePhotos(): List<TypePhoto>

    fun listAllTypePhotosLive(): LiveData<List<TypePhoto>>

    fun deleteTypePhotos()
}