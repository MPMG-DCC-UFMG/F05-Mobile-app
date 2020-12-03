package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote

class RemotePublicWorkDataSource(val mpApi: MPApi) : IRemotePublicWorkDataSource {

    override suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): ResponseRemote {
        return mpApi.sendPublicWork(publicWorkRemote)
    }
}