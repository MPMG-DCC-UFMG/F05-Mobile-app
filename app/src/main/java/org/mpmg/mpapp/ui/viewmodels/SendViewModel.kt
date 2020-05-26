package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import org.mpmg.mpapp.workers.PublicWorkUpload

class SendViewModel(private val applicationContext: Context) : ViewModel() {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun sendData() {
        WorkManager.getInstance(applicationContext).enqueue(createSendPublicWorkRequest())
    }

    private fun createSendPublicWorkRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(PublicWorkUpload::class.java)
            .setConstraints(constraints)
            .build()
    }
}