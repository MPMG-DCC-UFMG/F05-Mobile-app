package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.network.models.*
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

    override suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote> {
        return remoteConfigDataSource.loadPublicWorksDiff(version)
    }

    override suspend fun getAssociationsVersion(): EntityVersion {
        return remoteConfigDataSource.getAssociationsVersion()
    }

    override suspend fun loadAssociations(): List<AssociationTPTWRemote> {
        return remoteConfigDataSource.loadAssociations()
    }

    override fun saveAssociationsVersion(associationVersion: Int) {
        localConfigDataSource.saveAssociationsVersion(associationVersion)
    }

    override fun currentAssociationVersion(): Int {
        return localConfigDataSource.currentAssociationVersion()
    }

    override suspend fun getWorkStatusVersion(): EntityVersion {
        return remoteConfigDataSource.getWorkStatusVersion()
    }

    override suspend fun loadWorkStatus(): List<WorkStatusRemote> {
        return remoteConfigDataSource.loadWorkStatus()
    }

    override fun currentWorkStatusVersion(): Int {
        return localConfigDataSource.currentWorkStatusVersion()
    }

    override fun saveWorkStatusVersion(workStatusVersion: Int) {
        localConfigDataSource.saveWorkStatusVersion(workStatusVersion)
    }

    override suspend fun getCityVersion(): EntityVersion {
        return remoteConfigDataSource.getCityVersion()
    }

    override suspend fun loadCities(): List<CityRemote> {
        return remoteConfigDataSource.loadCities()
    }

    override fun currentCityVersion(): Int {
        return localConfigDataSource.currentCityVersion()
    }

    override fun saveCityVersion(cityVersion: Int) {
        localConfigDataSource.saveCityVersion(cityVersion)
    }
}