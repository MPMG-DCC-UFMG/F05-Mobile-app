package org.mpmg.mpapp.domain.repositories.config.datasources

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.TypePhotoRemote
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote

class RemoteConfigDataSource(private val mpApi: MPApi) : IRemoteConfigDataSource {

    override suspend fun loadTypeWorks(): List<TypeWorkRemote> {
        return mpApi.loadTypeWorks()
    }

    override suspend fun getTypeWorkVersion(): EntityVersion {
        return mpApi.getTypeWorkVersion()
    }

    override suspend fun loadPublicWorks(): List<PublicWorkRemote> {
        return mpApi.loadPublicWorks()
    }

    override suspend fun getPublicWorkVersion(): EntityVersion {
        return mpApi.getPublicWorkVersion()
    }

    override suspend fun loadTypePhotos(): List<TypePhotoRemote> {
        return mpApi.loadTypePhotos()
    }

    override suspend fun getTypePhotosVersion(): EntityVersion {
        return mpApi.getTypePhotosVersion()
    }
}