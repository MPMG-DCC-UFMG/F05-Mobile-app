package org.mpmg.mpapp.domain.repositories.association.datasources

import org.mpmg.mpapp.domain.database.models.AssociationTWTP

interface ILocalAssociationDataSource {

    fun insertAssociation(associationTWTP: AssociationTWTP)

    fun insertAssociations(associations: List<AssociationTWTP>)
}