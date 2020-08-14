package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.TypePhoto

data class TypePhotoRemote(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "flag") val flag: Int,
    @field:Json(name = "description") val description: String? = null
) {
    fun toTypePhotoDB(): TypePhoto {
        return TypePhoto(
            name = name,
            flag = flag,
            description = description
        )
    }
}