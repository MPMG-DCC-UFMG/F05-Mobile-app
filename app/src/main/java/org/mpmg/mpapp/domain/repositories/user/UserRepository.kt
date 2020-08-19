package org.mpmg.mpapp.domain.repositories.user

import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.network.models.TokenRemote
import org.mpmg.mpapp.domain.repositories.user.datasources.ILocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.IRemoteUserDataSource

class UserRepository(
    private val localUserDataSource: ILocalUserDataSource,
    private val remoteUserDataSource: IRemoteUserDataSource
) : IUserRepository {

    private val TAG: String = UserRepository::class.java.simpleName

    override fun insertUser(user: User) {
        localUserDataSource.insertUser(user)
    }

    override fun getUserByEmail(email: String): User? {
        return localUserDataSource.getUserByEmail(email)
    }

    override suspend fun createUser(mpUserRemote: MPUserRemote): ResponseRemote =
        remoteUserDataSource.createUser(mpUserRemote)

    override suspend fun login(username: String, password: String): TokenRemote =
        remoteUserDataSource.login(username, password)
}