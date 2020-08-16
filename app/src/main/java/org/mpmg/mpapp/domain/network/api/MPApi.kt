package org.mpmg.mpapp.domain.network.api

import okhttp3.MultipartBody
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

    @POST("publicworks/upsert")
    suspend fun sendPublicWork(@Body publicWorkRemote: PublicWorkRemote): PublicWorkRemote

    @GET("publicworks/changes")
    suspend fun getPublicWorksChange(@Query("version") version: Int): List<PublicWorkRemote>

    @POST("collects/add")
    suspend fun sendCollect(@Body collectRemote: CollectRemote): CollectRemote

    @POST("photos/add")
    suspend fun sendPhoto(@Body photoRemote: PhotoRemote): PhotoRemote

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
}