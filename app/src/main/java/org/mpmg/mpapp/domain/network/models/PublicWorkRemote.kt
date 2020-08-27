package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

data class PublicWorkRemote(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "address_id") var idAddress: String? = null,
    @field:Json(name = "type_work_flag") val typeWorkFlag: Int?,
    @field:Json(name = "address") var address: AddressRemote? = null,
    @field:Json(name = "operation") var operation: Int = 0,
    @field:Json(name = "user_status") var userStatus: Int = 0
) {

    constructor(publicWorkAndAddress: PublicWorkAndAddress) : this(
        id = publicWorkAndAddress.publicWork.id,
        name = publicWorkAndAddress.publicWork.name,
        typeWorkFlag = publicWorkAndAddress.publicWork.typeWorkFlag,
        userStatus = publicWorkAndAddress.publicWork.userStatusFlag,
        address = null
    ) {
        publicWorkAndAddress.address.let {
            this.address = AddressRemote(publicWorkAndAddress.address)
            this.idAddress = it.id
        }
    }

    fun toPublicWorkAndAddressDB(): PublicWorkAndAddress {
        val publicWorkDB = PublicWork(
            id = id,
            name = name?: "",
            typeWorkFlag = typeWorkFlag?: 0,
            toSend = false,
            userStatusFlag = userStatus
        )
        val addressDB = address!!.toAddressDB()
        return PublicWorkAndAddress(
            publicWork = publicWorkDB,
            address = addressDB
        )
    }
}