package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.AssociationTWTP

data class AssociationTPTWRemote(
    @field:Json(name = "type_work_flag") val typeWorkFlag: Int,
    @field:Json(name = "type_photo_flag") val typePhotoFlag: Int
) {
    fun toAssociationTWTPDB(): AssociationTWTP {
        return AssociationTWTP(typePhotoFlag = typePhotoFlag, typeWorkFlag = typeWorkFlag)
    }
}