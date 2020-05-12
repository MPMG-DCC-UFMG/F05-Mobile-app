package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mpmg.mpapp.domain.database.models.User

class UserTest {

    @Test
    fun testUserCreation() {
        val user = User(
            name = "Walter",
            email = "walter@test.com"
        )
        assertEquals("Walter", user.name)
        assertEquals("walter@test.com", user.email)
    }
}