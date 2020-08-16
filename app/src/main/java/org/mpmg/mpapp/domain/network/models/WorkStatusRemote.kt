package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.WorkStatus

data class WorkStatusRemote(
    @field:Json(name = "flag") val flag: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String?
) {
    fun toWorkStatusDB(): WorkStatus {
        return WorkStatus(
            flag = flag,
            name = name,
            description = description
        )
    }
}