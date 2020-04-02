package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.PublicWork

interface IPublicWorkRepository {

    fun insertPublicWork(publicWork: PublicWork)

    fun listAllPublicWorks(): List<PublicWork>

    fun listAllPublicWorksLive(): LiveData<List<PublicWork>>

}