package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.network.models.*
import org.mpmg.mpapp.domain.repositories.config.datasources.LocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.RemoteConfigDataSource

class ConfigRepository(
    private val remoteConfigDataSource: RemoteConfigDataSource,
    private val localConfigDataSource: LocalConfigDataSource
) {

    suspend fun loadTypeWorks(): List<TypeWorkRemote> {
        return remoteConfigDataSource.loadTypeWorks()
    }

    suspend fun getTypeWorkVersion(): EntityVersion {
        return remoteConfigDataSource.getTypeWorkVersion()
    }

    suspend fun loadPublicWorks(): List<PublicWorkRemote> {
        return remoteConfigDataSource.loadPublicWorks()
    }

    suspend fun getPublicWorkVersion(): EntityVersion {
        return remoteConfigDataSource.getPublicWorkVersion()
    }

    fun saveTypeWorksVersion(typeWorksVersion: Int) {
        localConfigDataSource.saveTypeWorksVersion(typeWorksVersion)
    }

    fun currentTypeWorksVersion(): Int {
        return localConfigDataSource.currentTypeWorksVersion()
    }

    fun setLoggedUserEmail(email: String) {
        localConfigDataSource.setLoggedUserEmail(email)
    }

    fun getLoggedUserEmail(): String {
        return localConfigDataSource.getLoggedUserEmail()
    }

    fun savePublicWorkVersion(publicWorkVersion: Int) {
        localConfigDataSource.savePublicWorkVersion(publicWorkVersion)
    }

    fun currentPublicWorkVersion(): Int {
        return localConfigDataSource.currentPublicWorkVersion()
    }

    suspend fun loadTypePhotos(): List<TypePhotoRemote> {
        return remoteConfigDataSource.loadTypePhotos()
    }

    suspend fun getTypePhotosVersion(): EntityVersion {
        return remoteConfigDataSource.getTypePhotosVersion()
    }

    fun currentTypePhotosVersion(): Int {
        return localConfigDataSource.currentTypePhotosVersion()
    }

    fun saveTypePhotosVersion(typePhotosVersion: Int) {
        localConfigDataSource.saveTypePhotosVersion(typePhotosVersion)
    }

    suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote> {
        return remoteConfigDataSource.loadPublicWorksDiff(version)
    }

    suspend fun getAssociationsVersion(): EntityVersion {
        return remoteConfigDataSource.getAssociationsVersion()
    }

    suspend fun loadAssociations(): List<AssociationTPTWRemote> {
        return remoteConfigDataSource.loadAssociations()
    }

    fun saveAssociationsVersion(associationVersion: Int) {
        localConfigDataSource.saveAssociationsVersion(associationVersion)
    }

    fun currentAssociationVersion(): Int {
        return localConfigDataSource.currentAssociationVersion()
    }

    suspend fun getWorkStatusVersion(): EntityVersion {
        return remoteConfigDataSource.getWorkStatusVersion()
    }

    suspend fun loadWorkStatus(): List<WorkStatusRemote> {
        return remoteConfigDataSource.loadWorkStatus()
    }

    fun currentWorkStatusVersion(): Int {
        return localConfigDataSource.currentWorkStatusVersion()
    }

    fun saveWorkStatusVersion(workStatusVersion: Int) {
        localConfigDataSource.saveWorkStatusVersion(workStatusVersion)
    }

    suspend fun getCityVersion(): EntityVersion {
        return remoteConfigDataSource.getCityVersion()
    }

    suspend fun loadCities(): List<CityRemote> {
        return remoteConfigDataSource.loadCities()
    }

    fun currentCityVersion(): Int {
        return localConfigDataSource.currentCityVersion()
    }

    fun saveCityVersion(cityVersion: Int) {
        localConfigDataSource.saveCityVersion(cityVersion)
    }
}