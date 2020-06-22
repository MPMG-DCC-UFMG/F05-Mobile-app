package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.WORKER_PARAMETER_PUBLIC_WORK_ID
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.ui.screens.upload.models.PublicWorkUploadUI
import org.mpmg.mpapp.workers.PublicWorkUpload
import java.util.*
import java.util.concurrent.ExecutionException

class SendViewModel(
    private val applicationContext: Context,
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val publicWorksMediated = MutableLiveData<List<PublicWorkUploadUI>>()

    private val workManagerInstance = WorkManager.getInstance(applicationContext)

    fun sendData() {
        initWorkers()
    }

    fun loadPublicWorksToSend() {
        viewModelScope.launch(Dispatchers.IO) {
            val publicWorkToSend =
                publicWorkRepository.listPublicWorkToSend().map { publicWork ->
                    val publicWorkUI = PublicWorkUploadUI(
                        name = publicWork.name,
                        id = publicWork.id,
                        idCollect = publicWork.idCollect,
                        toSend = publicWork.toSend,
                        _status = getPublicWorkStatus(publicWork),
                        _workState = WorkInfo.State.BLOCKED
                    )
                    publicWorkUI.workerInfoId.postValue(getPublicWorkUploadInfoId(publicWork.id))
                    publicWorkUI
                }

            if (publicWorkToSend.isNotEmpty()) {
                publicWorkToSend.last().showDivider = false
            }
            publicWorksMediated.postValue(publicWorkToSend)
        }

    }

    private fun getPublicWorkStatus(publicWork: PublicWork): String {
        val objectsToSend = mutableListOf<String>()
        if (publicWork.toSend) {
            objectsToSend.add(applicationContext.getString(R.string.single_public_work_data))
        }
        if (publicWork.idCollect != null) {
            objectsToSend.add(applicationContext.getString(R.string.single_collect))
        }
        return String.format(
            applicationContext.getString(R.string.text_to_send),
            objectsToSend.joinToString(",")
        )
    }

    private fun initWorkers() {
        publicWorksMediated.value?.let { publicWorkList ->
            publicWorkList.forEach {
                if (getPublicWorkUploadInfoId(it.id) == null) {
                    workManagerInstance.beginUniqueWork(
                        it.id,
                        ExistingWorkPolicy.REPLACE,
                        createSendPublicWorkRequest(it)
                    ).enqueue()
                    it.workerInfoId.postValue(getPublicWorkUploadInfoId(it.id))
                }
            }
        }
    }

    private fun getPublicWorkUploadInfoId(publicWorkId: String): UUID? {
        val status = workManagerInstance.getWorkInfosForUniqueWork(publicWorkId)
        return try {
            val workInfoList: List<WorkInfo> = status.get()
            for (workInfo in workInfoList) {
                val state = workInfo.state
                if (state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED) {
                    return workInfo.id
                }
            }
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        }
    }

    private fun createSendPublicWorkRequest(publicWork: PublicWorkUploadUI): OneTimeWorkRequest {
        val data = Data.Builder()
        data.putString(WORKER_PARAMETER_PUBLIC_WORK_ID, publicWork.id)

        return OneTimeWorkRequest.Builder(PublicWorkUpload::class.java)
            .setInputData(data.build())
            .addTag(publicWork.id)
            .setConstraints(constraints)
            .build()
    }

    fun getPublicWorkList() = publicWorksMediated
}