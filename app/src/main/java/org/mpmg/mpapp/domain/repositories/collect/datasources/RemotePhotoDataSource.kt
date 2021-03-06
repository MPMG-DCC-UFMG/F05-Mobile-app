package org.mpmg.mpapp.domain.repositories.collect.datasources

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import java.io.File

class RemotePhotoDataSource(val mpApi: MPApi) {
    suspend fun sendImage(image: File): ImageUploadResponse {
        val requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val fileForm = MultipartBody.Part.createFormData("file", image.name, requestFile);
        return mpApi.sendImage(fileForm)
    }

    suspend fun sendPhoto(photo: PhotoRemote): ResponseRemote {
        return mpApi.sendPhoto(photo)
    }
}