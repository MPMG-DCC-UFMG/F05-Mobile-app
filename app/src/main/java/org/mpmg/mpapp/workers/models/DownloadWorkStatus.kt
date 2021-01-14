package org.mpmg.mpapp.workers.models

import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.WorkStatusRemote
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository

class DownloadWorkStatus : BaseDownloadInfo<WorkStatusRemote>() {

    private val workStatusRepository: WorkStatusRepository by inject()

    override fun resourceId(): Int = R.string.progress_work_status

    override fun currentVersion(): Int = configRepository.currentWorkStatusVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getWorkStatusVersion()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveWorkStatusVersion(serverVersion)

    override suspend fun loadInfo(): Array<WorkStatusRemote> =
        configRepository.loadWorkStatus().toTypedArray()

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<WorkStatusRemote>()) {
            workStatusRepository.deleteWorkStatuses()
            workStatusRepository.insertWorkStatuses(list.map { workStatus ->
                workStatus as WorkStatusRemote
                workStatus.toWorkStatusDB()
            })
            true
        } else {
            false
        }
    }
}