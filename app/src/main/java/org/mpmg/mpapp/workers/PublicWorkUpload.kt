package org.mpmg.mpapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class PublicWorkUpload(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val publicWorkRepository: IPublicWorkRepository by inject()

    override suspend fun doWork(): Result {

        val publicWorkList = publicWorkRepository.listPublicWorksByStatus(false)

        publicWorkList.forEach { publicWorkAndAddress ->
            val publicWorkRemote = PublicWorkRemote(publicWorkAndAddress)
            kotlin.runCatching {
                publicWorkRepository.sendPublicWork(publicWorkRemote)
            }.onSuccess {
                markPublicWorkSent(publicWorkAndAddress.publicWork)
            }
        }

        return Result.success()
    }

    private fun markPublicWorkSent(publicWork: PublicWork) {

    }
}