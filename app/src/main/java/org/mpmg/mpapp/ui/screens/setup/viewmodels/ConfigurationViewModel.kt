package org.mpmg.mpapp.ui.screens.setup.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.work.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.workers.BaseWorker
import org.mpmg.mpapp.workers.LoadServerDataWorker

class ConfigurationViewModel(private val applicationContext: Context) : MVVMViewModel() {

    private val TAG = ConfigurationViewModel::class.java.name

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val navigateToList = SingleLiveEvent<Boolean>()
    val progressMessage = MutableLiveData<String>()
    val showTryAgain = MutableLiveData<Boolean>()

    private val WORKER_TAG = "SYNC_DATA_WORKER"

    fun startConfigFilesDownload(context: Context): LiveData<WorkInfo> {

        progressMessage.postValue(context.getString(R.string.progress_starting_download_data))
        showTryAgain.postValue(false)

        val workerRequest = OneTimeWorkRequest.Builder(LoadServerDataWorker::class.java)
            .addTag(WORKER_TAG)
            .setConstraints(constraints)
            .build()

        val workManagerInstance = WorkManager.getInstance(applicationContext)

        workManagerInstance.beginUniqueWork(
            WORKER_TAG,
            ExistingWorkPolicy.REPLACE,
            workerRequest
        ).enqueue()

        return workManagerInstance.getWorkInfoByIdLiveData(workerRequest.id)
    }


    fun handleNewWorkInfo(context: Context, info: WorkInfo) {
        showTryAgain.postValue(false)
        when (info.state) {
            WorkInfo.State.BLOCKED, WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING -> {
                val message = info.progress.getString(BaseWorker.Message)
                progressMessage.postValue(message)
            }
            WorkInfo.State.SUCCEEDED -> navigateToList()
            WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                showTryAgain.postValue(true)
                progressMessage.postValue(
                    context.getString(R.string.progress_download_failed_try_again)
                )
            }
        }
    }

    private fun navigateToList() {
        navigateToList.postValue(true)
    }
}