package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress

data class PublicWorkRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "address_id") val idAddress: String,
    @field:Json(name = "type_work_flag") val typeWorkFlag: Int,
    @field:Json(name = "address") val address: AddressRemote
) {

    fun toPublicWorkAndAddressDB(): PublicWorkAndAdress {
        val publicWorkDB = PublicWork(
            id = id,
            name = name,
            typeWorkFlag = typeWorkFlag
        )
        val addressDB = address.toAddressDB(publicWorkDB.id)
        return PublicWorkAndAdress(
            publicWork = publicWorkDB,
            address = addressDB
        )
    }
}