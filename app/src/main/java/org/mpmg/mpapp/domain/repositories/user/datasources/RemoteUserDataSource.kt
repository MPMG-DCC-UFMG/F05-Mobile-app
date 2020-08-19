package org.mpmg.mpapp.domain.repositories.user.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.MPUserRemote

class RemoteUserDataSource(private val mpApi: MPApi) : IRemoteUserDataSource {

    override suspend fun createUser(mpUserRemote: MPUserRemote) = mpApi.createUser(mpUserRemote)
}