package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.models.User
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class PublicWorkDAOTest {

    private lateinit var publicWorkDAO: PublicWorkDAO
    private lateinit var db: MPDatabase

    private val typeWork = TypeWork(flag = 1, name = "TEST1")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        publicWorkDAO = db.publicWorkDAO()
        db.typeWorkDAO().insert(typeWork)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writePublicWorkAndListAll() {
        val publicWork1 = PublicWork(
            id = "T1",
            name = "Test",
            latitude = 0.0,
            longitude = 0.0,
            typeWorkFlag = typeWork.flag
        )
        val publicWork2 = PublicWork(
            id = "T2",
            name = "Test",
            latitude = 0.0,
            longitude = 0.0,
            typeWorkFlag = typeWork.flag
        )
        publicWorkDAO.insertAll(arrayOf(publicWork1, publicWork2))
        val publicWorks = publicWorkDAO.listAllPublicWork()
        assertEquals(2, publicWorks.size)
    }
}