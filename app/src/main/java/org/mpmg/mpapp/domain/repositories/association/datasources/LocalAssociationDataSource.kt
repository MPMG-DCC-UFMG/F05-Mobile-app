package org.mpmg.mpapp.domain.repositories.association.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalAssociationDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertAssociation(associationTWTP: AssociationTWTP) {
        mpDatabase()!!.associationDAO().insert(associationTWTP)
    }

    fun insertAssociations(associations: List<AssociationTWTP>) {
        mpDatabase()!!.associationDAO().insertAll(associations.toTypedArray())
    }

    fun deleteAssociations() {
        mpDatabase()!!.associationDAO().deleteAll()
    }
}