package org.mpmg.mpapp.domain.repositories.collect.datasources

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import java.io.File

class RemotePhotoDataSource(val mpApi: MPApi) : IRemotePhotoDataSource {

    override suspend fun sendImage(image: File): ImageUploadResponse {
        val requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val fileForm = MultipartBody.Part.createFormData("file", image.name, requestFile);
        return mpApi.sendImage(fileForm)
    }

    override suspend fun sendPhoto(photo: PhotoRemote): PhotoRemote {
        return mpApi.sendPhoto(photo)
    }
}