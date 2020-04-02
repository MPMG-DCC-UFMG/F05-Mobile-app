package org.mpmg.mpapp.domain.repositories.user.local

import org.mpmg.mpapp.domain.models.User

interface ILocalUserDataSource {
    fun insertUser(user: User)

    fun getUserByEmail(email: String): User?
}