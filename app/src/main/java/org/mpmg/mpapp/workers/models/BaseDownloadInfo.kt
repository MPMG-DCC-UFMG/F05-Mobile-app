package org.mpmg.mpapp.workers.models

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository

abstract class BaseDownloadInfo<T> : KoinComponent {

    protected val configRepository: IConfigRepository by inject()

    abstract fun resourceId(): Int

    abstract fun currentVersion(): Int

    abstract suspend fun serverVersion(): EntityVersion

    abstract fun updateCurrentVersion(serverVersion: Int)

    abstract suspend fun loadInfo(): Array<T>

    abstract fun onSuccess(list: Array<*>): Boolean
}