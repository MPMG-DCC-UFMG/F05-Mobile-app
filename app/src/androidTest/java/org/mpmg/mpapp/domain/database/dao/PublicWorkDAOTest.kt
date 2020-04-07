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
import org.mpmg.mpapp.domain.models.Address
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
    private val publicWork1 = PublicWork(
        id = "T1",
        name = "Test",
        typeWorkFlag = typeWork.flag
    )
    private val publicWork2 = PublicWork(
        id = "T2",
        name = "Test",
        typeWorkFlag = typeWork.flag
    )
    private val address1 = Address(
        id = "1",
        number = "23",
        street = "test",
        neighborhood = "Test",
        latitude = 0.0,
        longitude = 0.0,
        city = "BH",
        state = "MG",
        cep = "34453-344",
        idPublicWork = publicWork1.id
    )
    private val address2 = Address(
        id = "2",
        number = "23",
        street = "test2",
        neighborhood = "Test2",
        latitude = 0.0,
        longitude = 0.0,
        city = "BH",
        state = "MG",
        cep = "34453-344",
        idPublicWork = publicWork2.id
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        publicWorkDAO = db.publicWorkDAO()
        db.typeWorkDAO().insert(typeWork)
        publicWorkDAO.insertAll(arrayOf(publicWork1, publicWork2))
        db.addressDAO().insertAll(arrayOf(address1, address2))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1ListAll() {
        val publicWorks = publicWorkDAO.listAllPublicWork()
        assertEquals(2, publicWorks.size)
    }

    @Test
    @Throws(Exception::class)
    fun test2getPublicWorkAndAddressById() {
        val publicWorkAndAdress = publicWorkDAO.getPublicWorkAndAddressById(publicWork1.id)
        publicWorkAndAdress?.let {
            assertEquals(address1.street, publicWorkAndAdress.address.street)
        } ?: assert(false)
    }

    @Test
    @Throws(Exception::class)
    fun test3ListAllPublicWorkAndAddress() {
        val publicWorksAndAdress = publicWorkDAO.listAllPublicWorkAndAddress()
        assertEquals(2, publicWorksAndAdress.size)
    }
}