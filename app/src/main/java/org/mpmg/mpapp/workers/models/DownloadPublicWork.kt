package org.mpmg.mpapp.workers.models

import org.koin.core.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.AssociationTPTWRemote
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class DownloadPublicWork : BaseDownloadInfo<PublicWorkRemote>() {

    private val publicWorkRepository: IPublicWorkRepository by inject()

    override fun resourceId(): Int = R.string.progress_public_work

    override fun currentVersion(): Int = configRepository.currentPublicWorkVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getPublicWorkVersion()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.savePublicWorkVersion(serverVersion)

    override suspend fun loadInfo(): Array<PublicWorkRemote> =
        configRepository.loadPublicWorksDiff(currentVersion()).toTypedArray()

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<PublicWorkRemote>()) {
            handlePublicWorkDiff(list.map { it as PublicWorkRemote })
            true
        } else {
            false
        }
    }

    private fun handlePublicWorkDiff(listPublicWorkRemote: List<PublicWorkRemote>) {
        listPublicWorkRemote.forEach {
            if (it.operation == 2) {
                publicWorkRepository.deletePublicWork(it.id)
            } else {
                publicWorkRepository.insertPublicWork(it.toPublicWorkAndAddressDB())
            }
        }
    }
}