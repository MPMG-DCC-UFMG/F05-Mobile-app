package org.mpmg.mpapp.domain.repositories.workstatus

import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.LocalWorkStatusDataSource

class WorkStatusRepository(private val localWorkStatusDataSource: LocalWorkStatusDataSource) {
    fun insertWorkStatus(workStatus: WorkStatus) {
        localWorkStatusDataSource.insertWorkStatus(workStatus)
    }

    fun insertWorkStatuses(workStatuses: List<WorkStatus>) {
        localWorkStatusDataSource.insertWorkStatuses(workStatuses)
    }

    fun listWorkStatusByIds(workStatusIds: List<Int>): List<WorkStatus> {
        return localWorkStatusDataSource.listWorkStatusByIds(workStatusIds)
    }

    fun listAllWorkStatus() = localWorkStatusDataSource.listAllWorkStatus()

    fun deleteWorkStatuses() {
        localWorkStatusDataSource.deleteWorkStatuses()
    }
}