package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.ILocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.IRemotePublicWorkDataSource

class PublicWorkRepository(
    private val localPublicWorkDataSource: ILocalPublicWorkDataSource,
    private val remotePublicWorkDataSource: IRemotePublicWorkDataSource
) :
    IPublicWorkRepository {

    private val TAG: String = PublicWorkRepository::class.java.simpleName

    override fun insertPublicWork(publicWork: PublicWork, address: Address) {
        localPublicWorkDataSource.insertPublicWork(publicWork, address)
    }

    override fun listAllPublicWorks(): List<PublicWorkAndAddress> {
        return localPublicWorkDataSource.listAllPublicWorks()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAddress>> {
        return localPublicWorkDataSource.listAllPublicWorksLive()
    }

    override fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAddress> {
        return localPublicWorkDataSource.getPublicWorkByIdLive(publicWorkId)
    }

    override fun getPublicWorkById(publicWorkId: String): PublicWorkAndAddress? {
        return localPublicWorkDataSource.getPublicWorkById(publicWorkId)
    }

    override fun insertPublicWorks(publicWorkAndAddresses: List<PublicWorkAndAddress>) {
        publicWorkAndAddresses.forEach {
            insertPublicWork(it.publicWork, it.address)
        }
    }

    override fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress> {
        return localPublicWorkDataSource.listPublicWorksByStatus(toSend)
    }

    override fun listPublicWorksByStatusLive(status: Boolean): LiveData<List<PublicWork>> {
        return localPublicWorkDataSource.listPublicWorksByStatusLive(status)
    }

    override suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): PublicWorkRemote {
        return remotePublicWorkDataSource.sendPublicWork(publicWorkRemote)
    }

    override fun markPublicWorkSent(publicWorkId: String) {
        localPublicWorkDataSource.markPublicWorkSent(publicWorkId)
    }

    override fun unlinkCollectFromPublicWork(publicWorkId: String) {
        localPublicWorkDataSource.unlinkCollectFromPublicWork(publicWorkId)
    }

    override fun listPublicWorkToSend(): List<PublicWork> {
        return localPublicWorkDataSource.listPublicWorkToSend()
    }

    override fun listPublicWorkToSendLive(): LiveData<List<PublicWork>> {
        return localPublicWorkDataSource.listPublicWorkToSendLive()
    }

    override fun countPublicWorkToSend(): Int {
        return localPublicWorkDataSource.countPublicWorkToSend()
    }

    override fun countPublicWorkToSendLive(): LiveData<Int> {
        return localPublicWorkDataSource.countPublicWorkToSendLive()
    }
}