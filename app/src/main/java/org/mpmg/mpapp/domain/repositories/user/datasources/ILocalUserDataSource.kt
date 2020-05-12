package org.mpmg.mpapp.domain.repositories.user.datasources

import org.mpmg.mpapp.domain.database.models.User

interface ILocalUserDataSource {
    fun insertUser(user: User)

    fun getUserByEmail(email: String): User?
}