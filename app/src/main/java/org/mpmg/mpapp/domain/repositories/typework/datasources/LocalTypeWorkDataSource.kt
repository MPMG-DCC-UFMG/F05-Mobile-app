package org.mpmg.mpapp.domain.repositories.typework.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypeWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalTypeWorkDataSource {

    override fun insertTypeWork(typeWork: TypeWork) {
        mpDatabase()!!.typeWorkDAO().insert(typeWork)
    }

    override fun insertTypeWorks(typeWorks: List<TypeWork>) {
        mpDatabase()!!.typeWorkDAO().insertAll(typeWorks.toTypedArray())
    }

    override fun listAllTypeWorks(): List<TypeWork> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWork()
    }

    override fun listAllTypeWorksLive(): LiveData<List<TypeWork>> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWorkLive()
    }

    override fun deleteTypeWorks() {
        mpDatabase()!!.typeWorkDAO().deleteAll()
    }
}