package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.TypeWork

data class TypeWorkRemote(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "flag") val flag: Int
) {
    fun toTypeWorkDB(): TypeWork {
        return TypeWork(
            name = name,
            flag = flag
        )
    }
}