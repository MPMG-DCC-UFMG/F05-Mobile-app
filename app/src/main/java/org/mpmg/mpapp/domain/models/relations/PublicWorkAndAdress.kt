package org.mpmg.mpapp.domain.models.relations

import androidx.databinding.Bindable
import androidx.room.Embedded
import androidx.room.Relation
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.DatabaseConstants
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork

data class PublicWorkAndAdress(
    @Embedded val publicWork: PublicWork,
    @Relation(
        parentColumn = DatabaseConstants.PublicWork.id,
        entityColumn = DatabaseConstants.Address.idPublicWork
    )
    val address: Address
) : BaseModel {

    fun isValid(): Boolean {
        return address.isValid() && publicWork.isValid() && address.idPublicWork == publicWork.id
    }
}