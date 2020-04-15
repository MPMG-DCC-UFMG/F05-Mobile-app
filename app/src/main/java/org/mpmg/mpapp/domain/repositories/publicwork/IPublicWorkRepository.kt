package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

interface IPublicWorkRepository {

    fun insertPublicWork(publicWork: PublicWork, address: Address)

    fun listAllPublicWorks(): List<PublicWorkAndAdress>

    fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>>

    fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAdress>

}