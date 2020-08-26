package org.mpmg.mpapp.domain.repositories.association

import org.mpmg.mpapp.domain.database.models.AssociationTWTP
import org.mpmg.mpapp.domain.repositories.association.datasources.ILocalAssociationDataSource

class AssociationRepository(private val localAssociationDataSource: ILocalAssociationDataSource) :
    IAssociationRepository {

    override fun insertAssociation(associationTWTP: AssociationTWTP) {
        localAssociationDataSource.insertAssociation(associationTWTP)
    }

    override fun insertAssociations(associations: List<AssociationTWTP>) {
        localAssociationDataSource.insertAssociations(associations)
    }

    override fun deleteAssociations() {
        localAssociationDataSource.deleteAssociations()
    }
}