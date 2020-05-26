package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import org.mpmg.mpapp.domain.network.models.PublicWorkRemote

interface IRemotePublicWorkDataSource {

    suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): PublicWorkRemote

}