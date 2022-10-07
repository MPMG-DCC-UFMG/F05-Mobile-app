package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.Address
import org.mpmg.mpapp.domain.database.models.Inspection
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

//"flag": 1,
//"name": "Vistoria 1",
//"description": "Descriçao da vistoria",
//"public_work_id": null,
//"collect_id": null,
//"status": 1,
//"user_status": null

data class InspectionRemote(
    @field:Json(name = "flag") val flag: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "public_work_id") val publicWorkId: String? = null,
    @field:Json(name = "collect_id") val collectId: String? = null,
    @field:Json(name = "status") var status: Int,
    @field:Json(name = "user_status") var userStatus: String? = null
) {
    constructor(inspection: Inspection) : this(
        name = inspection.name,
        flag = inspection.flag,
        description = inspection.description,
        publicWorkId = inspection.publicWorkId,
        collectId = inspection.collectId,
        status = inspection.status,
        userStatus = inspection.userStatus
    )

    fun toInspectionDB(): Inspection {
        return Inspection(
            name = name,
            flag = flag,
            description = description?: "",
            publicWorkId = publicWorkId?: "",
            collectId = collectId?: "",
            status = status,
            userStatus = userStatus?: ""
        )
    }
}