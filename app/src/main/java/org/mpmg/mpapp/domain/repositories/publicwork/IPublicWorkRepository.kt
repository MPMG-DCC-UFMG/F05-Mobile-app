package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress

interface IPublicWorkRepository {

    fun insertPublicWork(publicWork: PublicWork, address: Address)

    fun insertPublicWorks(publicWorkAndAddress: List<PublicWorkAndAdress>)

    fun listAllPublicWorks(): List<PublicWorkAndAdress>

    fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>>

    fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAdress>

}