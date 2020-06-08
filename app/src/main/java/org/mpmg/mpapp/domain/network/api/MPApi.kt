package org.mpmg.mpapp.domain.network.api

import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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

    @POST("collects/add")
    suspend fun sendCollect(@Body collectRemote: CollectRemote): CollectRemote
}