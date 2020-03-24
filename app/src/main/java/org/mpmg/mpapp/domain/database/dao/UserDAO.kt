package org.mpmg.mpapp.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query(
        "SELECT * FROM ${DatabaseConstants.User.tableName} " +
                "WHERE ${DatabaseConstants.User.id} = :userId"
    )
    fun getUserById(userId: Int): User?

    @Query(
        "SELECT * FROM ${DatabaseConstants.User.tableName} " +
                "WHERE ${DatabaseConstants.User.email} = :email"
    )
    fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM ${DatabaseConstants.User.tableName}")
    fun listAllUsers(): List<User>
}