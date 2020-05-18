package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.database.models.*
import org.mpmg.mpapp.domain.models.*
import java.io.IOException

class PhotoDAOTest {

    private lateinit var photoDAO: PhotoDAO
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
    private val collect =
        Collect(
            id = "t32",
            idUser = user.email,
            date = 980L,
            idPublicWork = publicWork1.id
        )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        photoDAO = db.photoDAO()
        db.userDAO().insert(user)
        db.typeWorkDAO().insert(typeWork)
        db.publicWorkDAO().insert(publicWork1)
        db.collectDAO().insert(collect)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writePhotoAndListAll() {
        val photo1 =
            Photo(
                id = "8789Y",
                idCollect = collect.id,
                filepath = "test",
                latitude = 0.0,
                longitude = 0.0
            )
        val photo2 =
            Photo(
                id = "8789Z",
                idCollect = collect.id,
                filepath = "test",
                latitude = 0.0,
                longitude = 0.0
            )
        photoDAO.insertAll(arrayOf(photo1, photo2))
        val photos = photoDAO.listAllPhotos()
        assertEquals(2, photos.size)
    }
}