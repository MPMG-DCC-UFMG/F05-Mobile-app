package org.mpmg.mpapp.domain.repositories.user.local

import android.content.Context
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.User
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalUserDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalUserDataSource {

    override fun insertUser(user: User) {
        mpDatabase()?.userDAO()?.insert(user)
    }

    override fun getUserByEmail(email: String): User? {
        return mpDatabase()?.userDAO()?.getUserByEmail(email)
    }
}