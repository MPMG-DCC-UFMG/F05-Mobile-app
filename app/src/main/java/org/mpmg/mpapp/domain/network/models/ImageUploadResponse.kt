package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json

data class ImageUploadResponse(
    @field:Json(name = "filepath") val filepath: String
)