package org.mpmg.mpapp.domain.repositories.typework.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypeWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalTypeWorkDataSource {

    override fun insertTypeWork(typeWork: TypeWork) {
        val typeWorkDB = mpDatabase()!!.typeWorkDAO().getTypeWorkByFlag(typeWork.flag)
        typeWorkDB?.let {
            mpDatabase()!!.typeWorkDAO().update(typeWork)
        } ?: kotlin.run {
            mpDatabase()!!.typeWorkDAO().insert(typeWork)
        }
    }

    override fun insertTypeWorks(typeWorks: List<TypeWork>) {
        typeWorks.forEach {
            insertTypeWork(it)
        }
    }

    override fun listAllTypeWorks(): List<TypeWork> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWork()
    }

    override fun listAllTypeWorksLive(): LiveData<List<TypeWork>> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWorkLive()
    }
}