package org.mpmg.mpapp.domain.repositories.publicwork

import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.LocalInspectionDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.RemotePublicWorkDataSource

class PublicWorkRepository(
    private val localPublicWorkDataSource: LocalPublicWorkDataSource,
    private val remotePublicWorkDataSource: RemotePublicWorkDataSource
) {

    private val TAG: String = InspectionRepository::class.java.simpleName

    fun insertPublicWork(publicWork: PublicWork, address: Address) {
        localPublicWorkDataSource.insertPublicWork(publicWork, address)
    }

    fun listAllPublicWorks(): List<PublicWorkAndAddress> {
        return localPublicWorkDataSource.listAllPublicWorks()
    }

    fun listAllPublicWorksLive(): Flow<List<PublicWorkAndAddress>> {
        return localPublicWorkDataSource.listAllPublicWorksLive()
    }

    fun getPublicWorkByIdLive(publicWorkId: String): Flow<PublicWorkAndAddress> {
        return localPublicWorkDataSource.getPublicWorkByIdLive(publicWorkId)
    }

    fun getPublicWorkById(publicWorkId: String): PublicWorkAndAddress? {
        return localPublicWorkDataSource.getPublicWorkById(publicWorkId)
    }

    fun insertPublicWorks(publicWorkAndAddresses: List<PublicWorkAndAddress>) {
        publicWorkAndAddresses.forEach {
            insertPublicWork(it.publicWork, it.address)
        }
    }

    fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress> {
        return localPublicWorkDataSource.listPublicWorksByStatus(toSend)
    }

    fun listPublicWorksByStatusLive(status: Boolean): Flow<List<PublicWork>> {
        return localPublicWorkDataSource.listPublicWorksByStatusLive(status)
    }

    suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): ResponseRemote {
        return remotePublicWorkDataSource.sendPublicWork(publicWorkRemote)
    }

    fun markPublicWorkSent(publicWorkId: String) {
        localPublicWorkDataSource.markPublicWorkSent(publicWorkId)
    }

    fun unlinkCollectFromPublicWork(publicWorkId: String) {
        localPublicWorkDataSource.unlinkCollectFromPublicWork(publicWorkId)
    }

    fun listPublicWorkToSend(): List<PublicWork> {
        return localPublicWorkDataSource.listPublicWorkToSend()
    }

    fun listPublicWorkToSendLive(): Flow<List<PublicWork>> {
        return localPublicWorkDataSource.listPublicWorkToSendLive()
    }

    fun countPublicWorkToSend(): Int {
        return localPublicWorkDataSource.countPublicWorkToSend()
    }

    fun countPublicWorkToSendLive(): Flow<Int> {
        return localPublicWorkDataSource.countPublicWorkToSendLive()
    }

    fun deletePublicWork(publicWorkId: String) {
        return localPublicWorkDataSource.deletePublicWork(publicWorkId)
    }

    fun insertPublicWork(publicWorkAndAddress: PublicWorkAndAddress) {
        with(publicWorkAndAddress) {
            localPublicWorkDataSource.insertPublicWork(publicWork, address)
        }
    }

    fun updateWorkStatusPublicWork(publicWorkId: String, workStatusFlag: Int) {
        localPublicWorkDataSource.getPublicWorkById(publicWorkId)?.let {
            if (workStatusFlag != it.publicWork.userStatusFlag) {
                it.publicWork.toSend = true
                it.publicWork.userStatusFlag = workStatusFlag
                localPublicWorkDataSource.insertPublicWork(it.publicWork, it.address)
            }
        }
    }
}