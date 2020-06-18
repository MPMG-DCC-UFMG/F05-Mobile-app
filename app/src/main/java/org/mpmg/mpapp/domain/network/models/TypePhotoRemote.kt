package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.TypePhoto

data class TypePhotoRemote(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "flag") val flag: Int
) {
    fun toTypePhotoDB(): TypePhoto {
        return TypePhoto(
            name = name,
            flag = flag
        )
    }
}