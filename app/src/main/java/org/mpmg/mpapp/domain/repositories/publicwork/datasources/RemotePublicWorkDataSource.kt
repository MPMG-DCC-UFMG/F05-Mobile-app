package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote

class RemotePublicWorkDataSource(val mpApi: MPApi) : IRemotePublicWorkDataSource {

    override suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): PublicWorkRemote {
        return mpApi.sendPublicWork(publicWorkRemote)
    }
}