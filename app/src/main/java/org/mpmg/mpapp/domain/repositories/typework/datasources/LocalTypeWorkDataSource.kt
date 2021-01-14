package org.mpmg.mpapp.domain.repositories.typework.datasources

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypeWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertTypeWork(typeWork: TypeWork) {
        mpDatabase()!!.typeWorkDAO().insert(typeWork)
    }

    fun insertTypeWorks(typeWorks: List<TypeWork>) {
        mpDatabase()!!.typeWorkDAO().insertAll(typeWorks.toTypedArray())
    }

    fun listAllTypeWorks(): List<TypeWork> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWork()
    }

    fun listAllTypeWorksLive(): Flow<List<TypeWork>> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWorkLive()
    }

    fun deleteTypeWorks() {
        mpDatabase()!!.typeWorkDAO().deleteAll()
    }
}