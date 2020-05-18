package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mpmg.mpapp.domain.database.models.TypeWork

class TypeWorkTest {

    @Test
    fun testTypeWorkCreation() {
        val typeWork = TypeWork(
            flag = 1,
            name = "Test"
        )
        assertEquals(1, typeWork.flag)
        assertEquals("Test", typeWork.name)
    }
}