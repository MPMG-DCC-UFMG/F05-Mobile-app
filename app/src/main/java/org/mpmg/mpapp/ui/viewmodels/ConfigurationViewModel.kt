package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import org.mpmg.mpapp.workers.LoadServerDataWorker

class ConfigurationViewModel(private val applicationContext: Context) : ViewModel() {

    private val TAG = ConfigurationViewModel::class.java.name

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val progressMessage = MutableLiveData<String>()
    val showTryAgain = MutableLiveData<Boolean>()

    private val WORKER_TAG = "SYNC_DATA_WORKER"

    fun startConfigFilesDownload(): LiveData<WorkInfo> {
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
}