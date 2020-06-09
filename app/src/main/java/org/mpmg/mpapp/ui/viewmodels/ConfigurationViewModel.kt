package org.mpmg.mpapp.ui.viewmodels

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typephoto.ITypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class ConfigurationViewModel(
    private val typeWorkRepository: ITypeWorkRepository,
    private val configRepository: IConfigRepository,
    private val publicWorkRepository: IPublicWorkRepository,
    private val typePhotoRepository: ITypePhotoRepository
) : ViewModel() {

    private val TAG = ConfigurationViewModel::class.java.name

    private val stepsFinished = MutableLiveData<MutableList<Boolean>>()

    init {
        stepsFinished.value = MutableList(3) { false }
    }

    fun startConfigFilesDownload() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTypeWorkList()
            downloadPublicWorkList()
            downloadTypePhotoList()
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
    private suspend fun downloadPublicWorkList() {
        val currentVersion = configRepository.currentPublicWorkVersion()
        val serverVersionResult: Result<EntityVersion> =
            kotlin.runCatching { configRepository.getPublicWorkVersion() }

        serverVersionResult.onSuccess { publicWorkVersion ->
            if (currentVersion != publicWorkVersion.version) {
                kotlin.runCatching {
                    configRepository.loadPublicWorks()
                }.onSuccess { listPublicWorkRemote ->
                    publicWorkRepository.insertPublicWorks(listPublicWorkRemote.map { it.toPublicWorkAndAddressDB() })
                    configRepository.savePublicWorkVersion(publicWorkVersion.version)
                }
            }
        }.onFailure {
            Log.d(TAG, "failed to download type of works")
        }

        setTaskDone(1)
    }

    @WorkerThread
    private suspend fun downloadTypeWorkList() {
        val currentVersion = configRepository.currentTypeWorksVersion()
        val serverVersionResult = kotlin.runCatching { configRepository.getTypeWorkVersion() }

        serverVersionResult.onSuccess { serverVersion ->
            if (currentVersion != serverVersion.version) {
                kotlin.runCatching {
                    configRepository.loadTypeWorks()
                }.onSuccess {
                    typeWorkRepository.insertTypeWorks(it.map { it.toTypeWorkDB() })
                    configRepository.saveTypeWorksVersion(serverVersion.version)
                }
            }
        }.onFailure {
            Log.d(TAG, "failed to download type of works")
        }

        setTaskDone(0)
    }

    @WorkerThread
    private suspend fun downloadTypePhotoList() {
        val currentVersion = configRepository.currentTypePhotosVersion()
        val serverVersionResult = kotlin.runCatching { configRepository.getTypePhotosVersion() }

        serverVersionResult.onSuccess { serverVersion ->
            if (currentVersion != serverVersion.version) {
                kotlin.runCatching {
                    configRepository.loadTypePhotos()
                }.onSuccess {
                    typePhotoRepository.insertTypePhotos(it.map { it.toTypePhotoDB() })
                    configRepository.saveTypePhotosVersion(serverVersion.version)
                }
            }
        }.onFailure {
            Log.d(TAG, "failed to download type of photos")
        }

        setTaskDone(2)
    }

}