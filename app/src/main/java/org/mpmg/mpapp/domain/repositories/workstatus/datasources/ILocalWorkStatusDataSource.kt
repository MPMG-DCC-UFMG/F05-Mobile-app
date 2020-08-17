package org.mpmg.mpapp.domain.repositories.workstatus.datasources

import org.mpmg.mpapp.domain.database.models.WorkStatus

interface ILocalWorkStatusDataSource {

    fun insertWorkStatus(workStatus: WorkStatus)

    fun insertWorkStatuses(workStatuses: List<WorkStatus>)

    fun listWorkStatusByIds(workStatusIds: List<Int>): List<WorkStatus>
}