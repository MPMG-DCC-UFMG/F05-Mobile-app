package org.mpmg.mpapp.domain.network.api

import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote
import retrofit2.http.GET

interface MPApi {

    @GET("typeworks/")
    suspend fun loadTypeWorks(): List<TypeWorkRemote>

    @GET("typeworks/version")
    suspend fun getTypeWorkVersion(): EntityVersion
}