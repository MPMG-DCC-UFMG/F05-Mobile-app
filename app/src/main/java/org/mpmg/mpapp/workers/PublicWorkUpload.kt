package org.mpmg.mpapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.core.Constants.WORKER_PARAMETER_PUBLIC_WORK_ID
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class PublicWorkUpload(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val publicWorkRepository: IPublicWorkRepository by inject()

    companion object {
        const val Progress = "Progress"
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {

        val publicWorkId =
            inputData.getString(WORKER_PARAMETER_PUBLIC_WORK_ID) ?: return Result.failure()

        setProgress(workDataOf(Progress to 0, Message to "Carregando dados de obras públicas"))
        delay(10000)
        val publicWork = publicWorkRepository.getPublicWorkById(publicWorkId)

        publicWork?.let {
            setProgress(
                workDataOf(
                    Progress to 10,
                    Message to "Enviando dados da obra para o servidor"
                )
            )
            delay(10000)
            setProgress(workDataOf(Progress to 20, Message to "Carregando coleta"))
            delay(10000)
            setProgress(workDataOf(Progress to 30, Message to "Enviando coleta para o servidor"))

            delay(10000)
        }

        setProgress(workDataOf(Progress to 40, Message to "Finalizando trabalhos"))
        delay(10000)
        setProgress(workDataOf(Progress to 100, Message to "Dados atualizados"))

        return Result.success()
    }


//    private suspend fun uploadPublicWork(publicWorkAndAddress: PublicWorkAndAddress): Boolean {
//        val publicWorkRemote = PublicWorkRemote(publicWorkAndAddress)
//        kotlin.runCatching {
//            publicWorkRepository.sendPublicWork(publicWorkRemote)
//        }.onSuccess {
//            markPublicWorkSent(publicWorkAndAddress.publicWork)
//            return true
//        }.onFailure {
//            return false
//        }
//    }

    private fun markPublicWorkSent(publicWork: PublicWork) {
        publicWorkRepository.markPublicWorkSent(publicWorkId = publicWork.id)
    }
}