package org.mpmg.mpapp.domain.repositories.publicwork.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.PublicWork

class LocalPublicWorkDataSource(val applicationContext: Context) : ILocalPublicWorkDataSource {

    private fun mpDatabase(): MPDatabase? = MPDatabase.getInstance(applicationContext)

    override fun insertPublicWork(publicWork: PublicWork) {
        mpDatabase()!!.publicWorkDAO().insert(publicWork)
    }

    override fun listAllPublicWorks(): List<PublicWork> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWork()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWork>> {
//        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkLive()
        val liveData = MutableLiveData<List<PublicWork>>()
        liveData.value = listOf(
            PublicWork(
                id = "PW1",
                name = "E.E. Maurício Murgel",
                latitude = -19.9293798,
                longitude = -43.9804877,
                typeWorkFlag = 2
            ), PublicWork(
                id = "PW2",
                name = "E.E. Francisco Menezes Filho",
                latitude = -19.8735893,
                longitude = -43.9852848,
                typeWorkFlag = 2
            ), PublicWork(
                id = "PW3",
                name = "E.E. Jornalista Jorge Paes Sardinha",
                latitude = -19.8906198,
                longitude = -43.9934177,
                typeWorkFlag = 2
            ),PublicWork(
                id = "PW4",
                name = "E.E. Maurício Murgel",
                latitude = -19.9293798,
                longitude = -43.9804877,
                typeWorkFlag = 2
            ), PublicWork(
                id = "PW5",
                name = "E.E. Francisco Menezes Filho",
                latitude = -19.8735893,
                longitude = -43.9852848,
                typeWorkFlag = 2
            ), PublicWork(
                id = "PW6",
                name = "E.E. Jornalista Jorge Paes Sardinha",
                latitude = -19.8906198,
                longitude = -43.9934177,
                typeWorkFlag = 2
            ),
            PublicWork(
                id = "PW7",
                name = "E.E. Jornalista Jorge Paes Sardinha",
                latitude = -19.8906198,
                longitude = -43.9934177,
                typeWorkFlag = 2
            )
        )
        return liveData
    }
}