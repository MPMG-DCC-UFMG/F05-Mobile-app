package org.mpmg.mpapp.domain.repositories.association.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalAssociationDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalAssociationDataSource {

    override fun insertAssociation(associationTWTP: AssociationTWTP) {
        mpDatabase()!!.associationDAO().insert(associationTWTP)
    }

    override fun insertAssociations(associations: List<AssociationTWTP>) {
        mpDatabase()!!.associationDAO().insertAll(associations.toTypedArray())
    }

    override fun deleteAssociations() {
        mpDatabase()!!.associationDAO().deleteAll()
    }
}