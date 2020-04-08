package org.mpmg.mpapp.domain.repositories.publicwork.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

class LocalPublicWorkDataSource(val applicationContext: Context) : ILocalPublicWorkDataSource {

    private fun mpDatabase(): MPDatabase? = MPDatabase.getInstance(applicationContext)

    @Transaction
    override fun insertPublicWork(publicWork: PublicWork, address: Address) {
        mpDatabase()!!.publicWorkDAO().insert(publicWork)
        mpDatabase()!!.addressDAO().insert(address)
    }

    override fun listAllPublicWorks(): List<PublicWorkAndAdress> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddress()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddressLive()
    }
}