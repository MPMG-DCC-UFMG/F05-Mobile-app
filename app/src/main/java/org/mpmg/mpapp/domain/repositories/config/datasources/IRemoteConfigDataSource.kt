package org.mpmg.mpapp.domain.repositories.config.datasources

import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.network.models.*

interface IRemoteConfigDataSource {

    suspend fun loadTypeWorks(): List<TypeWorkRemote>

    suspend fun getTypeWorkVersion(): EntityVersion

    suspend fun loadPublicWorks(): List<PublicWorkRemote>

    suspend fun getPublicWorkVersion(): EntityVersion

    suspend fun loadTypePhotos(): List<TypePhotoRemote>

    suspend fun getTypePhotosVersion(): EntityVersion

    suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote>

    suspend fun getAssociationsVersion(): EntityVersion

    suspend fun loadAssociations(): List<AssociationTPTWRemote>

    suspend fun getWorkStatusVersion(): EntityVersion

    suspend fun loadWorkStatus(): List<WorkStatusRemote>

    suspend fun getCityVersion(): EntityVersion

    suspend fun loadCities(): List<CityRemote>

}