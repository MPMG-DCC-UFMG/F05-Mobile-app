package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalPublicWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalPublicWorkDataSource {

    @Transaction
    override fun insertPublicWork(publicWork: PublicWork, address: Address) {
        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWork.id)
        publicWork.idCollect = oldPublicWork?.idCollect
        mpDatabase()!!.publicWorkDAO().insert(publicWork)
        mpDatabase()!!.addressDAO().insert(address)
    }

    override fun listAllPublicWorks(): List<PublicWorkAndAdress> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddress()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddressLive()
    }

    override fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAdress> {
        return mpDatabase()!!.publicWorkDAO().getPublicWorkAndAddressByIdLive(publicWorkId)
    }
}