package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.models.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CollectDAOTest {

    private lateinit var collectDAO: CollectDAO
    private lateinit var db: MPDatabase
    private val user = User(
        name = "test",
        email = "test@test.com"
    )
    private val typeWork =
        TypeWork(flag = 1, name = "TEST1")
    private val publicWork1 = PublicWork(
        id = "T1",
        name = "Test",
        typeWorkFlag = typeWork.flag
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        collectDAO = db.collectDAO()
        db.userDAO().insert(user)
        db.typeWorkDAO().insert(typeWork)
        db.publicWorkDAO().insert(publicWork1)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writeCollectAndListAll() {

        val collect1 =
            Collect(
                id = "8U8UAD",
                idUser = user.email,
                date = 898892L,
                idPublicWork = publicWork1.id
            )
        val collect2 =
            Collect(
                id = "8U8UADT",
                idUser = user.email,
                date = 8988923L,
                idPublicWork = publicWork1.id
            )
        collectDAO.insertAll(arrayOf(collect1, collect2))
        val collects = collectDAO.listAllCollects()
        assertEquals(2, collects.size)
    }
}