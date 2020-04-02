package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.User

@Dao
abstract class UserDAO : BaseDAO<User> {

    @Query(
        "SELECT * FROM ${DatabaseConstants.User.tableName} " +
                "WHERE ${DatabaseConstants.User.id} = :userId"
    )
    abstract fun getUserById(userId: Int): User?

    @Query(
        "SELECT * FROM ${DatabaseConstants.User.tableName} " +
                "WHERE ${DatabaseConstants.User.email} = :email"
    )
    abstract fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM ${DatabaseConstants.User.tableName}")
    abstract fun listAllUsers(): List<User>
}