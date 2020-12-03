package org.mpmg.mpapp.domain.repositories.collect.datasources

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import java.io.File


class RemoteCollectDataSource(val mpApi: MPApi) : IRemoteCollectDataSource {

    override suspend fun sendCollect(collectRemote: CollectRemote): ResponseRemote {
        return mpApi.sendCollect(collectRemote)
    }
}