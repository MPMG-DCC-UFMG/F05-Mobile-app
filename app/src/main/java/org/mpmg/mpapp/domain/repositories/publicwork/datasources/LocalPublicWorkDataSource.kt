package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalPublicWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalPublicWorkDataSource {

    @Transaction
    override fun insertPublicWork(publicWork: PublicWork, address: Address) {
        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWork.id)
        publicWork.idCollect = oldPublicWork?.idCollect ?: publicWork.idCollect
        mpDatabase()!!.publicWorkDAO().insert(publicWork)
        mpDatabase()!!.addressDAO().insert(address)
    }

    override fun getPublicWorkById(publicWorkId: String): PublicWorkAndAddress? {
        return mpDatabase()!!.publicWorkDAO().getPublicWorkAndAddressById(publicWorkId)
    }

    override fun listAllPublicWorks(): List<PublicWorkAndAddress> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddress()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAddress>> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddressLive()
    }

    override fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAddress> {
        return mpDatabase()!!.publicWorkDAO().getPublicWorkAndAddressByIdLive(publicWorkId)
    }

    override fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddressByStatus(toSend)
    }

    override fun listPublicWorksByStatusLive(status: Boolean): LiveData<List<PublicWork>> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkByStatusLive(status)
    }

    override fun markPublicWorkSent(publicWorkId: String) {
        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWorkId)
        oldPublicWork?.let {
            it.toSend = false
            mpDatabase()!!.publicWorkDAO().update(it)
        }
    }

    override fun unlinkCollectFromPublicWork(publicWorkId: String) {
        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWorkId)
        oldPublicWork?.let {
            it.idCollect = null
            mpDatabase()!!.publicWorkDAO().update(it)
        }
    }

    override fun listPublicWorkToSendLive(): LiveData<List<PublicWork>> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkToSendLive()
    }

    override fun listPublicWorkToSend(): List<PublicWork> {
        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkToSend()
    }

    override fun countPublicWorkToSend(): Int {
        return mpDatabase()!!.publicWorkDAO().countPublicWorkToSend()
    }

    override fun countPublicWorkToSendLive(): LiveData<Int> {
        return mpDatabase()!!.publicWorkDAO().countPublicWorkToSendLive()
    }
}