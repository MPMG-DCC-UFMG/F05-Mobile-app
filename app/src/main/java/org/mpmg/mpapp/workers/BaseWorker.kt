package org.mpmg.mpapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
abstract class BaseWorker(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters) {

    companion object {
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {
        return execute()
    }

    protected suspend fun updateProgress(message: String) {
        setProgress(workDataOf(Message to message))
    }

    protected fun getString(resourceId: Int): String {
        return applicationContext.getString(resourceId)
    }

    protected fun getString(resourceId: Int, vararg nextResourcesIds: Int): String {
        val words = nextResourcesIds.map { getString(it) }
        return applicationContext.getString(resourceId)
            .format(words.joinToString(","))
    }

    abstract suspend fun execute(): Result
}