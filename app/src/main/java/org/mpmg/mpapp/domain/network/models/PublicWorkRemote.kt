package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

data class PublicWorkRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "address_id") val idAddress: String,
    @field:Json(name = "type_work_flag") val typeWorkFlag: Int,
    @field:Json(name = "address") var address: AddressRemote? = null
) {

    constructor(publicWorkAndAddress: PublicWorkAndAddress) : this(
        id = publicWorkAndAddress.publicWork.id,
        name = publicWorkAndAddress.publicWork.name,
        idAddress = publicWorkAndAddress.address.id,
        typeWorkFlag = publicWorkAndAddress.publicWork.typeWorkFlag,
        address = null
    ) {
        publicWorkAndAddress.address.let {
            this.address = AddressRemote(publicWorkAndAddress.address)
        }
    }

    fun toPublicWorkAndAddressDB(): PublicWorkAndAddress {
        val publicWorkDB = PublicWork(
            id = id,
            name = name,
            typeWorkFlag = typeWorkFlag,
            isSent = true
        )
        val addressDB = address!!.toAddressDB()
        return PublicWorkAndAddress(
            publicWork = publicWorkDB,
            address = addressDB
        )
    }
}