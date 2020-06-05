package org.mpmg.mpapp.domain.repositories.collect.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.CollectRemote

class RemoteCollectDataSource(val mpApi: MPApi) : IRemoteCollectDataSource {

    override suspend fun sendCollect(collectRemote: CollectRemote): CollectRemote {
        return mpApi.sendCollect(collectRemote)
    }
}