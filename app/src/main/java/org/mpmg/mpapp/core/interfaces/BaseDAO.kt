package org.mpmg.mpapp.core.interfaces

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(obj: Array<T>)

    @Update
    fun update(vararg obj: T)
}