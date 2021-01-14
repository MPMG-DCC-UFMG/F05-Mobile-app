package org.mpmg.mpapp.domain.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.core.interfaces.BaseDAO
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.database.models.TypePhoto

@Dao
abstract class TypePhotoDAO : BaseDAO<TypePhoto> {

    @Query("SELECT * FROM ${DatabaseConstants.TypePhoto.tableName}")
    abstract fun listAllTypePhotos(): List<TypePhoto>

    @Query("SELECT * FROM ${DatabaseConstants.TypePhoto.tableName}")
    abstract fun listAllTypePhotosLive(): Flow<List<TypePhoto>>

    @Query("SELECT * from ${DatabaseConstants.TypePhoto.tableName} WHERE ${DatabaseConstants.TypePhoto.flag} = :flag")
    abstract fun getTypePhotoByFlag(flag: Int): TypePhoto?

    @Query("DELETE FROM ${DatabaseConstants.TypePhoto.tableName}")
    abstract fun deleteAll()
}