package org.mpmg.mpapp.workers.models

import android.util.Log
import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.InspectionRemote
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository

class DownloadInspections : BaseDownloadInfo<InspectionRemote>() {

    private val inspectionRepository: InspectionRepository by inject()

    override fun resourceId(): Int = R.string.progress_inspection

    override fun currentVersion(): Int = configRepository.currentInspectionVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getInspectionVersion()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveInspectionVersion(serverVersion)

    override suspend fun loadInfo(): Array<InspectionRemote> =
//        configRepository.loadInspectionsDiff(currentVersion()).toTypedArray()
        configRepository.loadInspections().toTypedArray()

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<InspectionRemote>()) {
            handleInspectionDiff(list.map { it as InspectionRemote })
            true
        } else {
            false
        }
    }

    private fun handleInspectionDiff(listInspectionRemote: List<InspectionRemote>) {
        listInspectionRemote.forEach {
            inspectionRepository.insertInspection(it.toInspectionDB())
        }
    }
}