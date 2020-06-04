package org.mpmg.mpapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.WORKER_PARAMETER_PUBLIC_WORK_ID
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class PublicWorkUpload(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val publicWorkRepository: IPublicWorkRepository by inject()
    private val collectRepository: ICollectRepository by inject()

    companion object {
        const val Progress = "Progress"
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {

        val publicWorkId =
            inputData.getString(WORKER_PARAMETER_PUBLIC_WORK_ID) ?: return Result.failure()

        updateProgress(0, getString(R.string.progress_loading_public_work))
        delay(10000)
        val publicWorkAndAddress = publicWorkRepository.getPublicWorkById(publicWorkId)

        publicWorkAndAddress?.let {
            if (publicWorkAndAddress.publicWork.toSend) {

                updateProgress(10, getString(R.string.progress_sending_public_work))

                kotlin.runCatching {
                    publicWorkRepository.sendPublicWork(PublicWorkRemote(it))
                }.onSuccess {
                    markPublicWorkSent(publicWorkAndAddress.publicWork)
                }.onFailure {
                    return Result.failure()
                }
            }
            updateProgress(20, getString(R.string.progress_public_work_sent))

            val collectId = publicWorkAndAddress.publicWork.idCollect
            if (collectId != null) {

                updateProgress(30, getString(R.string.progress_loading_collect))
                val collect = collectRepository.getCollect(collectId) ?: return Result.failure()
                delay(10000)
                updateProgress(40, getString(R.string.progress_sending_collect))
                kotlin.runCatching {
                    collectRepository.sendCollect(CollectRemote(collect))
                }.onSuccess {
                    markCollectSent(publicWorkAndAddress.publicWork)
                }.onFailure {
                    return Result.failure()
                }
                delay(10000)
            }
        }

        updateProgress(50, getString(R.string.progress_finishing_upload))
        delay(10000)
        updateProgress(100, getString(R.string.progress_success_upload))

        return Result.success()
    }

    private fun getString(resourceId: Int): String {
        return applicationContext.getString(resourceId)
    }

    private suspend fun updateProgress(progress: Int, message: String) {
        setProgress(workDataOf(Progress to progress, Message to message))
    }

    private fun markPublicWorkSent(publicWork: PublicWork) {
        publicWorkRepository.markPublicWorkSent(publicWorkId = publicWork.id)
    }

    private fun markCollectSent(publicWork: PublicWork) {
        publicWorkRepository.markCollectSent(publicWorkId = publicWork.id)
    }
}