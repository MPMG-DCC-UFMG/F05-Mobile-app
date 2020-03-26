package org.mpmg.mpapp.domain.models

import org.junit.Assert
import org.junit.Test

class CollectTest {

    @Test
    fun testCollectCreation() {
        val collect =
            Collect(id = "8U8UAD", idUser = "test@gmail.com", idWork = "eqwe12", date = 898892L)
        Assert.assertEquals("8U8UAD", collect.id)
        Assert.assertEquals("test@gmail.com", collect.idUser)
        Assert.assertEquals("eqwe12", collect.idWork)
        Assert.assertEquals(898892L, collect.date)
    }
}