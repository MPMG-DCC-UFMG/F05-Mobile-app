package org.mpmg.mpapp.domain.models

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeWorkTest {

    @Test
    fun testTypeWork_creation() {
        val typeWork = TypeWork(
            flag = 1,
            name = "Test"
        )
        assertEquals(1, typeWork.flag)
        assertEquals("Test", typeWork.name)
    }
}