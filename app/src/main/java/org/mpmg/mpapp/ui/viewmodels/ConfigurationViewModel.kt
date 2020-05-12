package org.mpmg.mpapp.ui.viewmodels

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class ConfigurationViewModel(
    private val typeWorkRepository: ITypeWorkRepository,
    private val configRepository: IConfigRepository
) : ViewModel() {

    private val TAG = ConfigurationViewModel::class.java.name

    private val stepsFinished = MutableLiveData<MutableList<Boolean>>()

    init {
        stepsFinished.value = MutableList(1) { false }
    }

    fun startConfigFilesDownload() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTaskTypeList()
        }
    }

    fun getStepsFinished() = stepsFinished

    private fun setTaskDone(index: Int) {
        stepsFinished.value?.let {
            val newList = it
            newList[index] = true
            stepsFinished.postValue(newList)
        }
    }

    @WorkerThread
    private suspend fun downloadTaskTypeList() {
        val currentVersion = configRepository.currentTypeWorksVersion()
        val serverVersionResult = kotlin.runCatching { configRepository.getTypeWorkVersion() }

        serverVersionResult.onSuccess {
            if (currentVersion != it.version) {
                kotlin.runCatching {
                    configRepository.loadTypeWorks()
                }.onSuccess {
                    typeWorkRepository.insertTypeWorks(it.map { it.toTypeWorkDB() })
                }
            }
        }.onFailure {
            Log.d(TAG, "failed to download type of works")
        }

        setTaskDone(0)
    }

}