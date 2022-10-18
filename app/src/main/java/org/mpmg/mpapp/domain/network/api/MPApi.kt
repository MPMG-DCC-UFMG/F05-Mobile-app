package org.mpmg.mpapp.domain.network.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.network.models.*
import retrofit2.http.*

interface MPApi {

    @GET("typeworks/")
    suspend fun loadTypeWorks(): List<TypeWorkRemote>

    @GET("typeworks/version")
    suspend fun getTypeWorkVersion(): EntityVersion

    @GET("publicworks/")
    suspend fun loadPublicWorks(): List<PublicWorkRemote>

    @GET("publicworks/version")
    suspend fun getPublicWorkVersion(): EntityVersion

    @GET("inspections/version") //TODO create endpoint in the server
    suspend fun getInspectionVersion(): EntityVersion

    @GET("inspections/")
    suspend fun loadInspections(): List<InspectionRemote>

//    @POST("inspections/add")
//    suspend fun sendSurveyWork(@Body surveyWorkRemote: SurveyWorkRemote): ResponseRemote
//
//    @PUT("inspections/update")
//    suspend fun sendSurveyWork(): List<SurveyWorkRemote>


    @POST("queue/publicwork/add")
    suspend fun sendPublicWork(@Body publicWorkRemote: PublicWorkRemote): ResponseRemote

    @GET("publicworks/changes")
    suspend fun getPublicWorksChange(@Query("version") version: Int): List<PublicWorkRemote>

    @GET("inspections/changes") //TODO create endpoint in the server
    suspend fun getInspectionsChange(@Query("version") version: Int): List<InspectionRemote>

    @POST("queue/collect/add")
    suspend fun sendCollect(@Body collectRemote: CollectRemote): ResponseRemote

    @POST("queue/photo/add")
    suspend fun sendPhoto(@Body photoRemote: PhotoRemote): ResponseRemote

    @Multipart
    @POST("images/upload")
    suspend fun sendImage(@Part fileForm: MultipartBody.Part): ImageUploadResponse

    @GET("typephotos/")
    suspend fun loadTypePhotos(): List<TypePhotoRemote>

    @GET("typephotos/version")
    suspend fun getTypePhotosVersion(): EntityVersion

    @GET("association/tptw/all")
    suspend fun loadAssociations(): List<AssociationTPTWRemote>

    @GET("association/tptw/version")
    suspend fun getAssociationsVersion(): EntityVersion

    @GET("workstatus/")
    suspend fun loadWorkStatus(): List<WorkStatusRemote>

    @GET("workstatus/version")
    suspend fun getWorkStatusVersion(): EntityVersion

    @GET("address/city/version")
    suspend fun getCitiesVersion(): EntityVersion

    @GET("address/city/all")
    suspend fun loadCities(): List<CityRemote>

    @POST("security/users/create")
    suspend fun createUser(@Body userRemote: MPUserRemote): ResponseRemote

    @FormUrlEncoded
    @POST("security/users/login")
    suspend fun login(
        @Field("username") request: String,
        @Field("password") password: String
    ): TokenRemote

    @GET("inspections/publicwork/{public_work_id}")
    suspend fun retrieveInspectionsByPublicWorkId(@Path("public_work_id") public_work_id: String):
            List<InspectionRemote>
}