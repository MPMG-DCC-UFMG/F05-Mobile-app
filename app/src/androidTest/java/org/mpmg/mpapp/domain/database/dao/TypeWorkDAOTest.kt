package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.models.User
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TypeWorkDAOTest {

    private lateinit var typeWorkDAO: TypeWorkDAO
    private lateinit var db: MPDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        typeWorkDAO = db.typeWorkDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writeTypeWorkAndListAll() {
        val typeWork1 = TypeWork(flag = 1,name = "TEST1")
        val typeWork2 = TypeWork(flag = 2,name = "TEST2")

        typeWorkDAO.insertAll(arrayOf(typeWork1,typeWork2))
        val typeWorks = typeWorkDAO.listAllTypeWork()
        assertEquals(2, typeWorks.size)
    }

}