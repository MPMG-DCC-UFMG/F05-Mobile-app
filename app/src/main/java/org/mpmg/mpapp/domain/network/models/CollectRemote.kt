package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.Collect

data class CollectRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "public_work_id") val idPublicWork: String,
    @field:Json(name = "user_email") val userEmail: String,
    @field:Json(name = "comments") val comment: String? = null,
    @field:Json(name = "date") val date: Long,
    @field:Json(name = "public_work_status") val publicWorkStatus: Int
) {
    constructor(collect: Collect) : this(
        id = collect.id,
        idPublicWork = collect.idPublicWork,
        userEmail = collect.idUser,
        comment = collect.comments,
        date = collect.date,
        publicWorkStatus = collect.publicWorkStatus
    )
}
