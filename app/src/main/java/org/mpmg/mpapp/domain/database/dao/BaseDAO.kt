package org.mpmg.mpapp.domain.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)


    @Insert
    fun insertAll(obj: Array<T>)
}