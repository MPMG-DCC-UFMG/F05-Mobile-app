package org.mpmg.mpapp.domain.repositories.config.datasources

import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.models.*

class RemoteConfigDataSource(private val mpApi: MPApi) {

    suspend fun loadTypeWorks(): List<TypeWorkRemote> {
        return mpApi.loadTypeWorks()
    }

    suspend fun getTypeWorkVersion(): EntityVersion {
        return mpApi.getTypeWorkVersion()
    }

    suspend fun loadPublicWorks(): List<PublicWorkRemote> {
        return mpApi.loadPublicWorks()
    }

    suspend fun getPublicWorkVersion(): EntityVersion {
        return mpApi.getPublicWorkVersion()
    }

    suspend fun getInspectionVersion(): EntityVersion {
        return mpApi.getInspectionVersion()
    }
    //Vistorias
//    suspend fun loadInspections(): List<SurveyWorkRemote> {
//        return mpApi.loadInspections()
//    }

    /*suspend fun getInspectionsAdd(): List<SurveyWorkRemote> {
        return mpApi.getInspections()
    }*/

    suspend fun loadTypePhotos(): List<TypePhotoRemote> {
        return mpApi.loadTypePhotos()
    }

    suspend fun getTypePhotosVersion(): EntityVersion {
        return mpApi.getTypePhotosVersion()
    }

    suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote> {
        return mpApi.getPublicWorksChange(version)
    }

    suspend fun loadInspections(): List<InspectionRemote> {
        return mpApi.loadInspections()
    }
//    suspend fun loadInspectionsDiff(version: Int): List<InspectionRemote> {
//        return mpApi.getInspectionsChange(version)
//    }

    suspend fun getAssociationsVersion(): EntityVersion {
        return mpApi.getAssociationsVersion()
    }

    suspend fun loadAssociations(): List<AssociationTPTWRemote> {
        return mpApi.loadAssociations()
    }

    suspend fun getWorkStatusVersion(): EntityVersion {
        return mpApi.getWorkStatusVersion()
    }

    suspend fun loadWorkStatus(): List<WorkStatusRemote> {
        return mpApi.loadWorkStatus()
    }

    suspend fun getCityVersion(): EntityVersion {
        return mpApi.getCitiesVersion()
    }

    suspend fun loadCities(): List<CityRemote> {
        return mpApi.loadCities()
    }
}