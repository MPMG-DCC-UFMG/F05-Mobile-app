package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.TypePhotoRemote
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote
import org.mpmg.mpapp.domain.repositories.config.datasources.ILocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.IRemoteConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.RemoteConfigDataSource

class ConfigRepository(
    private val remoteConfigDataSource: IRemoteConfigDataSource,
    private val localConfigDataSource: ILocalConfigDataSource
) :
    IConfigRepository {

    override suspend fun loadTypeWorks(): List<TypeWorkRemote> {
        return remoteConfigDataSource.loadTypeWorks()
    }

    override suspend fun getTypeWorkVersion(): EntityVersion {
        return remoteConfigDataSource.getTypeWorkVersion()
    }

    override suspend fun loadPublicWorks(): List<PublicWorkRemote> {
        return remoteConfigDataSource.loadPublicWorks()
    }

    override suspend fun getPublicWorkVersion(): EntityVersion {
        return remoteConfigDataSource.getPublicWorkVersion()
    }

    override fun saveTypeWorksVersion(typeWorksVersion: Int) {
        localConfigDataSource.saveTypeWorksVersion(typeWorksVersion)
    }

    override fun currentTypeWorksVersion(): Int {
        return localConfigDataSource.currentTypeWorksVersion()
    }

    override fun setLoggedUserEmail(email: String) {
        localConfigDataSource.setLoggedUserEmail(email)
    }

    override fun getLoggedUserEmail(): String {
        return localConfigDataSource.getLoggedUserEmail()
    }

    override fun savePublicWorkVersion(publicWorkVersion: Int) {
        localConfigDataSource.savePublicWorkVersion(publicWorkVersion)
    }

    override fun currentPublicWorkVersion(): Int {
        return localConfigDataSource.currentPublicWorkVersion()
    }

    override suspend fun loadTypePhotos(): List<TypePhotoRemote> {
        return remoteConfigDataSource.loadTypePhotos()
    }

    override suspend fun getTypePhotosVersion(): EntityVersion {
        return remoteConfigDataSource.getTypePhotosVersion()
    }

    override fun currentTypePhotosVersion(): Int {
        return localConfigDataSource.currentTypePhotosVersion()
    }

    override fun saveTypePhotosVersion(typePhotosVersion: Int) {
        localConfigDataSource.saveTypePhotosVersion(typePhotosVersion)
    }
}