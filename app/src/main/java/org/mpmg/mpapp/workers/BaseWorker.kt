package org.mpmg.mpapp.workers

import android.content.Context
import android.content.res.Resources
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.KoinComponent

abstract class BaseWorker(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    companion object {
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {
        return execute()
    }

    protected suspend fun updateProgress(message: String) {
        setProgress(workDataOf(LoadServerDataWorker.Message to message))
    }

    protected fun getString(resourceId: Int): String {
        return applicationContext.getString(resourceId)
    }

    protected fun getString(resourceId: Int, vararg nextResourcesIds: Int): String {
        return applicationContext.getString(resourceId).format(nextResourcesIds)
    }

    abstract suspend fun execute(): Result
}