package org.mpmg.mpapp.workers

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.WorkerParameters
import org.mpmg.mpapp.R
import org.mpmg.mpapp.workers.models.*

class LoadServerDataWorker(applicationContext: Context, parameters: WorkerParameters) :
    BaseWorker(applicationContext, parameters) {

    override suspend fun execute(): Result {
        val downloadCalls =
            listOf(
                DownloadTypeWork(),
                DownloadTypePhoto(),
                DownloadAssociation(),
                DownloadWorkStatus(),
                DownloadPublicWork()
            )

        for (call in downloadCalls) {
            if (!downloadData(call)) {
                return Result.failure()
            }
        }

        return Result.success()
    }

    @WorkerThread
    private suspend fun downloadData(downloadInfo: BaseDownloadInfo<*>): Boolean {
        val currentVersion = downloadInfo.currentVersion()
        val serverVersionResult = kotlin.runCatching { downloadInfo.serverVersion() }

        var actionResult = true

        updateProgress(getString(R.string.progress_check_version, downloadInfo.resourceId()))
        serverVersionResult.onSuccess { serverVersion ->
            if (currentVersion != serverVersion.version) {
                updateProgress(getString(R.string.progress_update, downloadInfo.resourceId()))
                kotlin.runCatching {
                    downloadInfo.loadInfo()
                }.onSuccess { serverList ->
                    actionResult = downloadInfo.onSuccess(serverList)
                    downloadInfo.updateCurrentVersion(serverVersion.version)
                }.onFailure {
                    updateProgress(
                        getString(R.string.progress_fail_download, downloadInfo.resourceId())
                    )
                    actionResult = false
                }
            }
        }.onFailure {
            updateProgress(getString(R.string.progress_fail_server))
            actionResult = false
        }

        return actionResult
    }
}