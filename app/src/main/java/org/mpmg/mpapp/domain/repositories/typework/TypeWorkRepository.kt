package org.mpmg.mpapp.domain.repositories.typework

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.datasources.ILocalTypeWorkDataSource

class TypeWorkRepository(private val localTypeWorkDataSource: ILocalTypeWorkDataSource) :
    ITypeWorkRepository {

    override fun insertTypeWork(typeWork: TypeWork) {
        localTypeWorkDataSource.insertTypeWork(typeWork)
    }

    override fun insertTypeWorks(typeWorks: List<TypeWork>) {
        localTypeWorkDataSource.insertTypeWorks(typeWorks)
    }

    override fun listAllTypeWorks(): List<TypeWork> {
        return localTypeWorkDataSource.listAllTypeWorks()
    }

    override fun listAllTypeWorksLive(): LiveData<List<TypeWork>> {
        return localTypeWorkDataSource.listAllTypeWorksLive()
    }
}