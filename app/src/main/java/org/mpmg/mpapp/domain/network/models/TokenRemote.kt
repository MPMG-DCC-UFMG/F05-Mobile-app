package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json

data class TokenRemote(
    @field:Json(name = "access_token") val token: String
)