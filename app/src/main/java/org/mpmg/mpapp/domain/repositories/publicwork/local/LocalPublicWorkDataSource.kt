package org.mpmg.mpapp.domain.repositories.publicwork.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalPublicWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext), ILocalPublicWorkDataSource {

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