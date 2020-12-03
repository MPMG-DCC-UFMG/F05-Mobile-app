package org.mpmg.mpapp.domain.repositories.collect.datasources

import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import java.io.File

interface IRemotePhotoDataSource {

    suspend fun sendImage(image: File): ImageUploadResponse

    suspend fun sendPhoto(photo: PhotoRemote): ResponseRemote

}