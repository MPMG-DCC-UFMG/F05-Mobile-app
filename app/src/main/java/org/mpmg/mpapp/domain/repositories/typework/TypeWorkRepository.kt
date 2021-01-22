package org.mpmg.mpapp.domain.repositories.typework

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.datasources.LocalTypeWorkDataSource

class TypeWorkRepository(private val localTypeWorkDataSource: LocalTypeWorkDataSource) {

    fun insertTypeWork(typeWork: TypeWork) {
        localTypeWorkDataSource.insertTypeWork(typeWork)
    }

    fun insertTypeWorks(typeWorks: List<TypeWork>) {
        localTypeWorkDataSource.insertTypeWorks(typeWorks)
    }

    fun listAllTypeWorks(): List<TypeWork> {
        return localTypeWorkDataSource.listAllTypeWorks()
    }

    fun listAllTypeWorksLive(): Flow<List<TypeWork>> {
        return localTypeWorkDataSource.listAllTypeWorksLive()
    }

    fun deleteTypeWorks() {
        localTypeWorkDataSource.deleteTypeWorks()
    }

    fun getTypeOfWorkFromFlag(typeWorkFlag: Int) =
        localTypeWorkDataSource.listAllTypeWorks().first { it.flag == typeWorkFlag }
}