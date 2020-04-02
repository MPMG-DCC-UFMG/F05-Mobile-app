package org.mpmg.mpapp.domain.repositories.user

import org.mpmg.mpapp.domain.models.User

interface IUserRepository {

    fun insertUser(user: User)

    fun getUserByEmail(email: String): User?
}