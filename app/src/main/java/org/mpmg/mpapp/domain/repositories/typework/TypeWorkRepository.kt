package org.mpmg.mpapp.domain.repositories.typework

import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.local.ILocalTypeWorkDataSource

class TypeWorkRepository(val localTypeWorkDataSource: ILocalTypeWorkDataSource) :
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
}