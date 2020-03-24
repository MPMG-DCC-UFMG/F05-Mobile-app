package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun testUserCreation() {
        val user = User(id = 1, name = "Walter", email = "walter@test.com")
        assertEquals(1, user.id)
        assertEquals("Walter", user.name)
        assertEquals("walter@test.com", user.email)
    }
}