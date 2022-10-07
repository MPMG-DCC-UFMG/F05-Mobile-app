package org.mpmg.mpapp.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.mpmg.mpapp.domain.database.dao.*
import org.mpmg.mpapp.domain.database.models.*

@Database(
    entities = [User::class, TypeWork::class, PublicWork::class, Inspection::class, Collect::class,
        Photo::class, Address::class, TypePhoto::class, AssociationTWTP::class,
        WorkStatus::class, City::class],
    version = 1,
    exportSchema = false
)
abstract class MPDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun typeWorkDAO(): TypeWorkDAO
    abstract fun publicWorkDAO(): PublicWorkDAO
    abstract fun inspectionDAO(): InspectionDAO
    abstract fun collectDAO(): CollectDAO
    abstract fun photoDAO(): PhotoDAO
    abstract fun addressDAO(): AddressDAO
    abstract fun typePhotoDAO(): TypePhotoDAO
    abstract fun associationDAO(): AssociationDAO
    abstract fun workStatusDAO(): WorkStatusDAO
    abstract fun cityDAO(): CityDAO

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