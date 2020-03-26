package org.mpmg.mpapp.domain.repositories.user.local

import android.content.Context
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.User

class LocalUserDataSource(val applicationContext: Context) : ILocalUserDataSource {

    private fun mpDatabase(): MPDatabase? {
        return MPDatabase.getInstance(applicationContext)
    }

    override fun insertUser(user: User) {
        mpDatabase()?.userDAO()?.insert(user)
    }

    override fun getUserById(userId: Int): User? {
        return mpDatabase()?.userDAO()?.getUserById(userId)
    }

    override fun getUserByEmail(email: String): User? {
        return mpDatabase()?.userDAO()?.getUserByEmail(email)
    }
}