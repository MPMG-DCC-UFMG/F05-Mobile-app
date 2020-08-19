package org.mpmg.mpapp.domain.repositories.user.datasources

import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote

interface IRemoteUserDataSource {

    suspend fun createUser(mpUserRemote: MPUserRemote) : ResponseRemote
}