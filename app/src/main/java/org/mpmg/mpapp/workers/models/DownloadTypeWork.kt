package org.mpmg.mpapp.workers.models

import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository

class DownloadTypeWork : BaseDownloadInfo<TypeWorkRemote>() {

    private val typeWorkRepository: TypeWorkRepository by inject()

    override fun resourceId(): Int = R.string.progress_type_work

    override fun currentVersion(): Int = configRepository.currentTypeWorksVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getTypeWorkVersion()

    override suspend fun loadInfo(): Array<TypeWorkRemote> =
        configRepository.loadTypeWorks().toTypedArray()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveTypeWorksVersion(serverVersion)

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<TypeWorkRemote>()) {
            typeWorkRepository.deleteTypeWorks()
            typeWorkRepository.insertTypeWorks(list.map { typeWork ->
                typeWork as TypeWorkRemote
                typeWork.toTypeWorkDB()
            })
            true
        } else {
            false
        }
    }
}