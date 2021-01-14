package org.mpmg.mpapp.domain.repositories.association

import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.repositories.association.datasources.LocalAssociationDataSource

class AssociationRepository(private val localAssociationDataSource: LocalAssociationDataSource) {

    fun insertAssociation(associationTWTP: AssociationTWTP) {
        localAssociationDataSource.insertAssociation(associationTWTP)
    }

    fun insertAssociations(associations: List<AssociationTWTP>) {
        localAssociationDataSource.insertAssociations(associations)
    }

    fun deleteAssociations() {
        localAssociationDataSource.deleteAssociations()
    }
}