package org.mpmg.mpapp.domain.repositories.typework.local

import org.mpmg.mpapp.domain.models.TypeWork

interface ILocalTypeWorkDataSource {

    fun insertTypeWork(typeWork: TypeWork)

    fun insertTypeWorks(typeWorks: List<TypeWork>)

    fun listAllTypeWorks(): List<TypeWork>
}