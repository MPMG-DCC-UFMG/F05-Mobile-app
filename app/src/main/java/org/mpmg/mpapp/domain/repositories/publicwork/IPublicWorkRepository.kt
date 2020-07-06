package org.mpmg.mpapp.domain.repositories.publicwork

import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.ILocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.IRemotePublicWorkDataSource

interface IPublicWorkRepository : ILocalPublicWorkDataSource, IRemotePublicWorkDataSource {

    fun insertPublicWorks(publicWorkAndAddresses: List<PublicWorkAndAddress>)

    fun insertPublicWork(publicWorkAndAddress: PublicWorkAndAddress)


}