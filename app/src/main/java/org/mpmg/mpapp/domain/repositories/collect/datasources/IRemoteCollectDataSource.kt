package org.mpmg.mpapp.domain.repositories.collect.datasources

import org.mpmg.mpapp.domain.network.models.CollectRemote

interface IRemoteCollectDataSource {

    suspend fun sendCollect(collectRemote: CollectRemote): CollectRemote

}