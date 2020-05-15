package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.Address

data class AddressRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "street") val street: String,
    @field:Json(name = "state") val state: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "number") val number: String,
    @field:Json(name = "neighborhood") val neighborhood: String,
    @field:Json(name = "cep") val cep: String,
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double
) {
    fun toAddressDB(idPublicWork: String): Address {
        return Address(
            id = id,
            idPublicWork = idPublicWork,
            street = street,
            state = state,
            city = city,
            number = number,
            neighborhood = neighborhood,
            cep = cep,
            latitude = latitude,
            longitude = longitude
        )
    }
}