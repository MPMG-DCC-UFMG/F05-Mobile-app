package org.mpmg.mpapp.domain.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MPDatabaseTest {

    @Test
    fun test1_creationOfDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, MPDatabase::class.java).build()
        assert(db.isOpen)
        db.close()
    }
}