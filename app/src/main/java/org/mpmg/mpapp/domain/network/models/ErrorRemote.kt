package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json

data class ErrorRemote(
    @field:Json(name = "status_code") val statusCode: Int,
    @field:Json(name = "message") val message: String
)