package org.mpmg.mpapp.domain.repositories.user

import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.network.models.TokenRemote
import org.mpmg.mpapp.domain.repositories.user.datasources.LocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.RemoteUserDataSource

class UserRepository(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) {

    private val TAG: String = UserRepository::class.java.simpleName

    fun insertUser(user: User) {
        localUserDataSource.insertUser(user)
    }

    fun getUserByEmail(email: String): User? {
        return localUserDataSource.getUserByEmail(email)
    }

    suspend fun createUser(mpUserRemote: MPUserRemote): ResponseRemote =
        remoteUserDataSource.createUser(mpUserRemote)

    suspend fun login(username: String, password: String): TokenRemote =
        remoteUserDataSource.login(username, password)
}