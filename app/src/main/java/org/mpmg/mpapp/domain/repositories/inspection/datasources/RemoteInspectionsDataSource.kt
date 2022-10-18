package org.mpmg.mpapp.domain.repositories.inspection.datasources

import org.mpmg.mpapp.domain.network.api.MPApi

class RemoteInspectionsDataSource(private val mpApi: MPApi) {

    suspend fun retrieveInspectionsByPublicWorkId(publicWorkId: String) =
        mpApi.retrieveInspectionsByPublicWorkId(publicWorkId)
}