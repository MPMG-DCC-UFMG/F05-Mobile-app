package org.mpmg.mpapp.domain.repositories.workstatus.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalWorkStatusDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalWorkStatusDataSource {

    override fun insertWorkStatus(workStatus: WorkStatus) {
        mpDatabase()!!.workStatusDAO().insert(workStatus)
    }

    override fun insertWorkStatuses(workStatuses: List<WorkStatus>) {
        mpDatabase()!!.workStatusDAO().insertAll(workStatuses.toTypedArray())
    }
}