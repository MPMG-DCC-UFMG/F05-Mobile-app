package org.mpmg.mpapp.domain.repositories.typework.datasources

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.TypeWork

interface ILocalTypeWorkDataSource {

    fun insertTypeWork(typeWork: TypeWork)

    fun insertTypeWorks(typeWorks: List<TypeWork>)

    fun listAllTypeWorks(): List<TypeWork>

    fun listAllTypeWorksLive() : LiveData<List<TypeWork>>

    fun deleteTypeWorks()
}