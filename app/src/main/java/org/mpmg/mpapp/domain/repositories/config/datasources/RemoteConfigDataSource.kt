package org.mpmg.mpapp.domain.repositories.config.datasources

import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.*

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

    override suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote> {
        return mpApi.getPublicWorksChange(version)
    }

    override suspend fun getAssociationsVersion(): EntityVersion {
        return mpApi.getAssociationsVersion()
    }

    override suspend fun loadAssociations(): List<AssociationTPTWRemote> {
        return mpApi.loadAssociations()
    }

    override suspend fun getWorkStatusVersion(): EntityVersion {
        return mpApi.getWorkStatusVersion()
    }

    override suspend fun loadWorkStatus(): List<WorkStatusRemote> {
        return mpApi.loadWorkStatus()
    }

    override suspend fun getCityVersion(): EntityVersion {
        return mpApi.getCitiesVersion()
    }

    override suspend fun loadCities(): List<CityRemote> {
        return mpApi.loadCities()
    }
}