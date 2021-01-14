package org.mpmg.mpapp.workers.models

import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.AssociationTPTWRemote
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.repositories.association.AssociationRepository

class DownloadAssociation : BaseDownloadInfo<AssociationTPTWRemote>() {

    private val associationRepository: AssociationRepository by inject()

    override fun resourceId(): Int = R.string.progress_association

    override fun currentVersion(): Int = configRepository.currentAssociationVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getAssociationsVersion()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveAssociationsVersion(serverVersion)

    override suspend fun loadInfo(): Array<AssociationTPTWRemote> =
        configRepository.loadAssociations().toTypedArray()

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<AssociationTPTWRemote>()) {
            associationRepository.deleteAssociations()
            associationRepository.insertAssociations(list.map { associations ->
                associations as AssociationTPTWRemote
                associations.toAssociationTWTPDB()
            })
            true
        } else {
            false
        }
    }
}