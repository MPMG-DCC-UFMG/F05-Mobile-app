package org.mpmg.mpapp.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.mpmg.mpapp.domain.database.dao.UserDAO
import org.mpmg.mpapp.domain.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MPDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        private var INSTANCE: MPDatabase? = null

        fun getInstance(context: Context): MPDatabase? {
            if (INSTANCE == null) {
                synchronized(MPDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MPDatabase::class.java, "MPDatabase.db"
                    ).build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE?.let {
                if (it.isOpen) {
                    it.close()
                }
            }
            INSTANCE = null
        }
    }
}