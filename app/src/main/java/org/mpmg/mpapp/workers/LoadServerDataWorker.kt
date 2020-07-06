package org.mpmg.mpapp.workers

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typephoto.ITypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class LoadServerDataWorker(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val typeWorkRepository: ITypeWorkRepository by inject()
    private val configRepository: IConfigRepository by inject()
    private val publicWorkRepository: IPublicWorkRepository by inject()
    private val typePhotoRepository: ITypePhotoRepository by inject()

    companion object {
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {
        if (!downloadTypeWorkList()) {
            return Result.failure()
        }

        if (!downloadPublicWorkList()) {
            return Result.failure()
        }

        if (!downloadTypePhotoList()) {
            return Result.failure()
        }

        return Result.success()
    }

    @WorkerThread
    private suspend fun downloadTypeWorkList(): Boolean {
        val currentVersion = configRepository.currentTypeWorksVersion()
        val serverVersionResult = kotlin.runCatching { configRepository.getTypeWorkVersion() }

        var actionResult = true

        updateProgress(getString(R.string.progress_check_type_work_version))
        serverVersionResult.onSuccess { serverVersion ->
            if (currentVersion != serverVersion.version) {
                updateProgress(getString(R.string.progress_update_type_work))
                kotlin.runCatching {
                    configRepository.loadTypeWorks()
                }.onSuccess {
                    typeWorkRepository.insertTypeWorks(it.map { it.toTypeWorkDB() })
                    configRepository.saveTypeWorksVersion(serverVersion.version)
                    actionResult = true
                }.onFailure {
                    updateProgress(getString(R.string.progress_fail_download_type_works))
                    actionResult = false
                }
            }
        }.onFailure {
            updateProgress(getString(R.string.progress_fail_server))
            actionResult = false
        }

        return actionResult
    }

    @WorkerThread
    private suspend fun downloadPublicWorkList(): Boolean {
        val currentVersion = configRepository.currentPublicWorkVersion()
        val serverVersionResult: kotlin.Result<EntityVersion> =
            kotlin.runCatching { configRepository.getPublicWorkVersion() }

        var actionResult = true

        updateProgress(getString(R.string.progress_check_public_work_version))
        serverVersionResult.onSuccess { publicWorkVersion ->
            if (currentVersion != publicWorkVersion.version) {
                updateProgress(getString(R.string.progress_update_public_work))
                kotlin.runCatching {
                    configRepository.loadPublicWorksDiff(currentVersion)
                }.onSuccess { listPublicWorkRemote ->
                    handlePublicWorkDiff(listPublicWorkRemote)
                    configRepository.savePublicWorkVersion(publicWorkVersion.version)
                    actionResult = true
                }.onFailure {
                    updateProgress(getString(R.string.progress_fail_download_public_work))
                    actionResult = false
                }
            }
        }.onFailure {
            updateProgress(getString(R.string.progress_fail_server))
            actionResult = false
        }

        return actionResult
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


    @WorkerThread
    private suspend fun downloadTypePhotoList(): Boolean {
        val currentVersion = configRepository.currentTypePhotosVersion()
        val serverVersionResult = kotlin.runCatching { configRepository.getTypePhotosVersion() }

        var actionResult = true

        updateProgress(getString(R.string.progress_check_type_photo_version))
        serverVersionResult.onSuccess { serverVersion ->
            if (currentVersion != serverVersion.version) {
                updateProgress(getString(R.string.progress_update_type_work))
                kotlin.runCatching {
                    configRepository.loadTypePhotos()
                }.onSuccess {
                    typePhotoRepository.insertTypePhotos(it.map { it.toTypePhotoDB() })
                    configRepository.saveTypePhotosVersion(serverVersion.version)
                }.onFailure {
                    updateProgress(getString(R.string.progress_fail_download_type_photo))
                    actionResult = false
                }
            }
        }.onFailure {
            updateProgress(getString(R.string.progress_fail_server))
            actionResult = false
        }

        return actionResult
    }

    private suspend fun updateProgress(message: String) {
        setProgress(workDataOf(Message to message))
    }

    private fun getString(resourceId: Int): String {
        return applicationContext.getString(resourceId)
    }
}