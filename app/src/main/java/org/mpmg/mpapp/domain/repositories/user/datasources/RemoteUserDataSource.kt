package org.mpmg.mpapp.domain.repositories.user.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.network.models.TokenRemote

class RemoteUserDataSource(private val mpApi: MPApi) {

    suspend fun createUser(mpUserRemote: MPUserRemote) = mpApi.createUser(mpUserRemote)

    suspend fun login(username: String, password: String): TokenRemote =
        mpApi.login(username, password)
}