package org.mpmg.mpapp.domain.models

import junit.framework.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun testUser_creation() {
        val user = User(id = 1, name = "Walter", email = "walter@test.com")
        assertEquals(1, user.id)
        assertEquals("Walter", user.name)
        assertEquals("walter@test.com", user.email)
    }
}