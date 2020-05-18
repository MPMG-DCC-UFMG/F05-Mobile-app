package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.TypeWork
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AddressDAOTest {

    private lateinit var addressDAO: AddressDAO
    private lateinit var db: MPDatabase
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
        db.typeWorkDAO().insert(typeWork)
        db.publicWorkDAO().insert(publicWork1)
        addressDAO = db.addressDAO()
        val address1 = Address(
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
        val address2 = Address(
            id = "2",
            number = "23",
            street = "test2",
            neighborhood = "Test2",
            latitude = 0.0,
            longitude = 0.0,
            city = "BH",
            state = "MG",
            cep = "34453-344",
            idPublicWork = publicWork1.id
        )
        addressDAO.insertAll(arrayOf(address1, address2))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writeAddressAndListAll() {
        val collects = addressDAO.listAllAddress()
        assertEquals(2, collects.size)
    }

    @Test
    @Throws(Exception::class)
    fun test2getAddressById() {
        val address = addressDAO.getAddressById("1")
        assertEquals("test",address.street)
    }
}