package org.mpmg.mpapp.domain.repositories.user.datasources

import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.network.models.TokenRemote

interface IRemoteUserDataSource {

    suspend fun createUser(mpUserRemote: MPUserRemote): ResponseRemote

    suspend fun login(username: String, password: String): TokenRemote
}