package org.mpmg.mpapp.domain.repositories.publicwork.local

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

interface ILocalPublicWorkDataSource {

    fun insertPublicWork(publicWork: PublicWork, address: Address)

    fun listAllPublicWorks(): List<PublicWorkAndAdress>

    fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>>
}