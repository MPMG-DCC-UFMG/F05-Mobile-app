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
    @field:Json(name = "longitude") val longitude: Double,
    @field:Json(name = "public_work_id") val publicWorkId: String
) {
    constructor(address: Address) : this(
        id = address.id,
        publicWorkId = address.idPublicWork,
        street = address.street,
        state = address.state,
        city = address.city,
        number = address.number,
        neighborhood = address.neighborhood,
        cep = address.cep,
        latitude = address.latitude ?: 0.0,
        longitude = address.longitude ?: 0.0
    )

    fun toAddressDB(): Address {
        return Address(
            id = id,
            idPublicWork = publicWorkId,
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