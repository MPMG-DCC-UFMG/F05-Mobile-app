package org.mpmg.mpapp.domain.repositories.inspections

import kotlinx.coroutines.flow.flow
import org.mpmg.mpapp.domain.repositories.inspections.datasources.RemoteInspectionsDataSource

class InspectionsRepository(private val remoteInspectionsDataSource: RemoteInspectionsDataSource) {

    suspend fun retrieveInspectionsByPublicWorkId(publicWorkId: String) = flow {
        emit(remoteInspectionsDataSource.retrieveInspectionsByPublicWorkId(publicWorkId))
    }

}