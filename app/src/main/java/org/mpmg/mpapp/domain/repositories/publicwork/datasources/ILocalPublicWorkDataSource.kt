package org.mpmg.mpapp.domain.repositories.publicwork.datasources

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

interface ILocalPublicWorkDataSource {

    fun insertPublicWork(publicWork: PublicWork, address: Address)

    fun listAllPublicWorks(): List<PublicWorkAndAddress>

    fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAddress>>

    fun getPublicWorkByIdLive(publicWorkId: String): LiveData<PublicWorkAndAddress>

    fun listPublicWorksByStatus(toSend: Boolean): List<PublicWorkAndAddress>

    fun listPublicWorksByStatusLive(status: Boolean): LiveData<List<PublicWork>>

    fun markPublicWorkSent(publicWorkId: String)
}