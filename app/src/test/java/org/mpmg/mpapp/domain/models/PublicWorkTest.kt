package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test

class PublicWorkTest {

    @Test
    fun testPublicWorkCreation() {
        val publicWork = PublicWork(
            id = "ID1",
            name = "TEST1",
            latitude = 0.0,
            longitude = 0.0,
            typeWorkFlag = 1
        )
        assertEquals("ID1", publicWork.id)
        assertEquals("TEST1", publicWork.name)
        assertEquals(1, publicWork.typeWorkFlag)
        assert(0.0 == publicWork.latitude)
        assert(0.0 == publicWork.longitude)
    }
}