package org.mpmg.mpapp.domain.repositories.collect.datasources

import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import java.io.File

interface IRemoteCollectDataSource {

    suspend fun sendCollect(collectRemote: CollectRemote): CollectRemote


}