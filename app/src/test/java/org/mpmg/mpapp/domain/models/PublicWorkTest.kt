package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mpmg.mpapp.domain.database.models.PublicWork

class PublicWorkTest {

    @Test
    fun testPublicWorkCreation() {
        val publicWork = PublicWork(
            id = "ID1",
            name = "TEST1",
            typeWorkFlag = 1
        )
        assertEquals("ID1", publicWork.id)
        assertEquals("TEST1", publicWork.name)
        assertEquals(1, publicWork.typeWorkFlag)
    }
}