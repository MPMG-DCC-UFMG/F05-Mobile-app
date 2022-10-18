package org.mpmg.mpapp.domain.repositories.publicwork

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mpmg.mpapp.domain.database.models.Inspection
import org.mpmg.mpapp.domain.repositories.inspection.datasources.RemoteInspectionsDataSource
import org.mpmg.mpapp.domain.repositories.inspection.datasources.LocalInspectionDataSource

class InspectionRepository(
    private val localInspectionDataSource: LocalInspectionDataSource,
    private val remoteInspectionDataSource: RemoteInspectionsDataSource
) {

    private val TAG: String = InspectionRepository::class.java.simpleName

//    fun insertPublicWork(publicWork: PublicWork, address: Address) {
//        localInspectionDataSource.insertPublicWork(publicWork, address)
//    }

    fun listAllInspections(): List<Inspection> {
        return localInspectionDataSource.listAllInspections()
    }

    fun listAllInspectionsLive(): Flow<List<Inspection>> {
        return localInspectionDataSource.listAllInspectionsLive()
    }

    fun getInspectionByNameLive(inspectionName: String): Flow<Inspection> {
        return localInspectionDataSource.getInspectionByNameLive(inspectionName)
    }

    fun getInspectionByName(inspectionName: String): Inspection? {
        return localInspectionDataSource.getInspectionByName(inspectionName)
    }

    suspend fun retrieveInspectionsByPublicWorkId(publicWorkId: String) = flow {
        emit(remoteInspectionDataSource.retrieveInspectionsByPublicWorkId(publicWorkId))
    }
//    fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress> {
//        return localInspectionDataSource.listPublicWorksByStatus(toSend)
//    }
//
//    fun listPublicWorksByStatusLive(status: Boolean): Flow<List<PublicWork>> {
//        return localInspectionDataSource.listPublicWorksByStatusLive(status)
//    }
//
//    suspend fun sendPublicWork(publicWorkRemote: PublicWorkRemote): ResponseRemote {
//        return remoteInspectionDataSource.sendPublicWork(publicWorkRemote)
//    }
//
//    fun markPublicWorkSent(publicWorkId: String) {
//        localInspectionDataSource.markPublicWorkSent(publicWorkId)
//    }
//
//    fun unlinkCollectFromPublicWork(publicWorkId: String) {
//        localInspectionDataSource.unlinkCollectFromPublicWork(publicWorkId)
//    }
//
//    fun listPublicWorkToSend(): List<PublicWork> {
//        return localInspectionDataSource.listPublicWorkToSend()
//    }
//
//    fun listPublicWorkToSendLive(): Flow<List<PublicWork>> {
//        return localInspectionDataSource.listPublicWorkToSendLive()
//    }
//
//    fun countPublicWorkToSend(): Int {
//        return localInspectionDataSource.countPublicWorkToSend()
//    }
//
//    fun countPublicWorkToSendLive(): Flow<Int> {
//        return localInspectionDataSource.countPublicWorkToSendLive()
//    }
//
//    fun deletePublicWork(publicWorkId: String) {
//        return localInspectionDataSource.deletePublicWork(publicWorkId)
//    }
//
    fun insertInspection(inspection: Inspection) {
        with(inspection) {
            localInspectionDataSource.insertInspection(inspection)
        }
    }
//
//    fun updateWorkStatusPublicWork(publicWorkId: String, workStatusFlag: Int) {
//        localInspectionDataSource.getPublicWorkById(publicWorkId)?.let {
//            if (workStatusFlag != it.publicWork.userStatusFlag) {
//                it.publicWork.toSend = true
//                it.publicWork.userStatusFlag = workStatusFlag
//                localInspectionDataSource.insertPublicWork(it.publicWork, it.address)
//            }
//        }
//    }
}