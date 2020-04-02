package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.repositories.publicwork.local.ILocalPublicWorkDataSource

class PublicWorkRepository(
    val localPublicWorkDataSource: ILocalPublicWorkDataSource
) :
    IPublicWorkRepository {

    private val TAG: String = PublicWorkRepository::class.java.simpleName

    override fun insertPublicWork(publicWork: PublicWork) {
        localPublicWorkDataSource.insertPublicWork(publicWork)
    }

    override fun listAllPublicWorks(): List<PublicWork> {
        return localPublicWorkDataSource.listAllPublicWorks()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWork>> {
        return localPublicWorkDataSource.listAllPublicWorksLive()
    }
}