package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json

data class EntityVersion(
    @field:Json(name = "version") val version: Int
)