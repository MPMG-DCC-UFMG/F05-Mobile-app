package org.mpmg.mpapp.domain.repositories.user

import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.repositories.user.datasources.ILocalUserDataSource

class UserRepository(private val localUserDataSource: ILocalUserDataSource) : IUserRepository {

    private val TAG: String = UserRepository::class.java.simpleName

    override fun insertUser(user: User) {
        localUserDataSource.insertUser(user)
    }

    override fun getUserByEmail(email: String): User? {
        return localUserDataSource.getUserByEmail(email)
    }
}