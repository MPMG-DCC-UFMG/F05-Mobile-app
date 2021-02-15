package org.mpmg.mpapp.domain.repositories.workstatus.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalWorkStatusDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertWorkStatus(workStatus: WorkStatus) {
        mpDatabase()!!.workStatusDAO().insert(workStatus)
    }

    fun insertWorkStatuses(workStatuses: List<WorkStatus>) {
        mpDatabase()!!.workStatusDAO().insertAll(workStatuses.toTypedArray())
    }

    fun listAllWorkStatus() = mpDatabase()!!.workStatusDAO().listAllWorkStatusLive()

    fun listWorkStatusByIds(workStatusIds: List<Int>): List<WorkStatus> {
        return mpDatabase()!!.workStatusDAO().listAllWorkStatusByFlags(workStatusIds)
    }

    fun deleteWorkStatuses() {
        mpDatabase()!!.workStatusDAO().deleteAll()
    }
}