package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.Photo

data class PhotoRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "id_collect") val idCollect: String,
    @field:Json(name = "filepath") val filepath: String,
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double,
    @field:Json(name = "comment") val comment: String?,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "timestamp") val timestamp: Long
) {
    constructor(photo: Photo, filepath: String) : this(
        id = photo.id,
        idCollect = photo.idCollect,
        filepath = filepath,
        latitude = photo.latitude,
        longitude = photo.longitude,
        comment = photo.comment,
        type = photo.type ?: "",
        timestamp = photo.timestamp
    )
}
