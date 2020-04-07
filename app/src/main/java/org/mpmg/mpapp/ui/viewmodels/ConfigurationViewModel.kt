package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class ConfigurationViewModel(
    private val typeWorkRepository: ITypeWorkRepository,
    private val configRepository: IConfigRepository
) : ViewModel() {

    private val numberOfSteps = 2
    private val steps = MutableLiveData<MutableList<Boolean>>()

    init {
        steps.value = mutableListOf()
    }

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    fun startConfigFilesDownload() {
        ioScope.launch {
            val currentVersion = configRepository.currentFilesVersion()
            val serverVersion = configRepository.getServerConfigFilesVersion()

            if (currentVersion == serverVersion) {
                setConfigurationDone()
            } else {

                val stepsResult = mutableListOf<Boolean>()

                stepsResult.add(downloadTaskTypeList())

                if (allStepsDone()) {
                    configRepository.saveConfigFilesVersion(serverVersion)
                }

                stepsResult.add(allStepsDone())
                steps.postValue(stepsResult)
            }
        }
    }

    fun getSetupSteps() = steps

    private fun setConfigurationDone() {
        steps.postValue(MutableList(numberOfSteps) { true })
    }

    private fun downloadTaskTypeList(): Boolean {
        val typesWorks = configRepository.loadListTypeWorks()
        typeWorkRepository.insertTypeWorks(typesWorks)
        return true
    }

    private fun allStepsDone(): Boolean {
        return steps.value?.contains(false) == false
    }

    fun isConfigurationDone() = (steps.value?.size ?: 0) >= numberOfSteps

}