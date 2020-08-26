package org.mpmg.mpapp.domain.repositories.workstatus

import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.ILocalWorkStatusDataSource

class WorkStatusRepository(private val localWorkStatusDataSource: ILocalWorkStatusDataSource) :
    IWorkStatusRepository {
    override fun insertWorkStatus(workStatus: WorkStatus) {
        localWorkStatusDataSource.insertWorkStatus(workStatus)
    }

    override fun insertWorkStatuses(workStatuses: List<WorkStatus>) {
        localWorkStatusDataSource.insertWorkStatuses(workStatuses)
    }

    override fun listWorkStatusByIds(workStatusIds: List<Int>): List<WorkStatus> {
        return localWorkStatusDataSource.listWorkStatusByIds(workStatusIds)
    }

    override fun deleteWorkStatuses() {
        localWorkStatusDataSource.deleteWorkStatuses()
    }
}