package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.City

data class CityRemote(
    @field:Json(name = "codigo_ibge") val codigoIbge: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "longitude") val longitude: Double,
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "uf") val uf: String
) {
    fun toCityDB(): City {
        return City(
            codigoIbge = codigoIbge,
            name = name,
            longitude = longitude,
            latitude = latitude,
            uf = uf
        )
    }
}