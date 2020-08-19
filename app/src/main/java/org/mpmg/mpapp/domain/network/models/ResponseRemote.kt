package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json

data class ResponseRemote(
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "error") val error: ErrorRemote? = null
)