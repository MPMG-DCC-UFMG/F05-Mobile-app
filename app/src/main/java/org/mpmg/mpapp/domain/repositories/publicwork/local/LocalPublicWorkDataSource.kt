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
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkLive()
    }
}