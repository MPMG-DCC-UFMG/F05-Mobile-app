package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import android.content.Context
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.Inspection
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalInspectionDataSource(applicationContext: Context) : BaseDataSource(applicationContext){

    @Transaction
    fun insertInspection(inspection: Inspection) {
        mpDatabase()!!.inspectionDAO().insert(inspection)
//        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWork.id)
//        oldPublicWork?.let {
//            publicWork.idCollect = oldPublicWork.idCollect ?: publicWork.idCollect
//            mpDatabase()!!.publicWorkDAO().update(publicWork)
//        }?: kotlin.run {
//            mpDatabase()!!.publicWorkDAO().insert(publicWork)
//        }
//        mpDatabase()!!.addressDAO().insert(address)
    }

    fun getInspectionByName(inspectionName: String): Inspection? {
        return mpDatabase()!!.inspectionDAO().getInspectionByName(inspectionName)
    }

    fun getInspectionByNameLive(inspectionName: String): Flow<Inspection> {
        return mpDatabase()!!.inspectionDAO().getInspectionByNameLive(inspectionName)
    }

    fun listAllInspections(): List<Inspection> {
        return mpDatabase()!!.inspectionDAO().listAllInspection()
    }

    fun listAllInspectionsLive(): Flow<List<Inspection>> {
        return mpDatabase()!!.inspectionDAO().listAllInspectionLive()
    }

//    fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress> {
//        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkAndAddressByStatus(toSend)
//    }
//
//    fun listPublicWorksByStatusLive(status: Boolean): Flow<List<PublicWork>> {
//        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkByStatusLive(status)
//    }
//
//    fun markPublicWorkSent(publicWorkId: String) {
//        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWorkId)
//        oldPublicWork?.let {
//            it.toSend = false
//            mpDatabase()!!.publicWorkDAO().update(it)
//        }
//    }
//
//    fun unlinkCollectFromPublicWork(publicWorkId: String) {
//        val oldPublicWork = mpDatabase()!!.publicWorkDAO().getPublicWorkById(publicWorkId)
//        oldPublicWork?.let {
//            it.idCollect = null
//            mpDatabase()!!.publicWorkDAO().update(it)
//        }
//    }
//
//    fun listPublicWorkToSendLive(): Flow<List<PublicWork>> {
//        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkToSendLive()
//    }
//
//    fun listPublicWorkToSend(): List<PublicWork> {
//        return mpDatabase()!!.publicWorkDAO().listAllPublicWorkToSend()
//    }
//
//    fun countPublicWorkToSend(): Int {
//        return mpDatabase()!!.publicWorkDAO().countPublicWorkToSend()
//    }
//
//    fun countPublicWorkToSendLive(): Flow<Int> {
//        return mpDatabase()!!.publicWorkDAO().countPublicWorkToSendLive()
//    }
//
//    fun deletePublicWork(publicWorkId: String) {
//        return mpDatabase()!!.publicWorkDAO().delete(publicWorkId)
//    }
}