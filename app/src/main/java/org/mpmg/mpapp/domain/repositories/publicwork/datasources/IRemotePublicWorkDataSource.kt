package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote

interface IRemotePublicWorkDataSource {

    suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): ResponseRemote
}