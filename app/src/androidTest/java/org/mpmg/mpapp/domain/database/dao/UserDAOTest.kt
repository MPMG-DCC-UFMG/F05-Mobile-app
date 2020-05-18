package org.mpmg.mpapp.domain.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.database.models.User
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDAOTest {

    private lateinit var userDao: UserDAO
    private lateinit var db: MPDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MPDatabase::class.java
        ).build()
        userDao = db.userDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test1writeUserAndListAll() {
        val user = User(
            name = "Test",
            email = "test@test.com"
        )
        val user2 = User(
            name = "Test",
            email = "test2@test.com"
        )
        userDao.insert(user)
        userDao.insert(user2)
        val usersList = userDao.listAllUsers()
        assertEquals(2, usersList.size)
    }

    @Test
    @Throws(Exception::class)
    fun test2insertAll() {
        val user = User(
            name = "Test",
            email = "test3@test.com"
        )
        val user2 = User(
            name = "Test",
            email = "test4@test.com"
        )
        userDao.insertAll(arrayOf(user, user2))
        val usersList = userDao.listAllUsers()
        assert(usersList.find { user.email == "test3@test.com" } != null)
        assert(usersList.find { user.email == "test4@test.com" } != null)
    }

    @Test
    @Throws(Exception::class)
    fun test3writeUserAndFindByEmail() {
        val user = User(
            name = "Test",
            email = "test5@test.com"
        )
        userDao.insert(user)
        val byName = userDao.getUserByEmail("test5@test.com")
        byName?.let {
            assert(byName.email == user.email)
        } ?: assert(false)
    }
}