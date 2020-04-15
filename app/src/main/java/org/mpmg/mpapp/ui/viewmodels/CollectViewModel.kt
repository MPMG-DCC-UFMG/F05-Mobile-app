package org.mpmg.mpapp.ui.viewmodels

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.Collect
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class CollectViewModel(
    private val collectRepository: ICollectRepository,
    private val configRepository: IConfigRepository,
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private var mSelectedPublicWork: LiveData<PublicWorkAndAdress> = MutableLiveData()
    private var mPhotoList = MutableLiveData<MutableList<Photo>>()

    private val currentCollect = MutableLiveData<Collect>()

    private fun newCollect(publicWorkId: String) {
        val currentUser = configRepository.getLoggedUserEmail()
        currentCollect.postValue(Collect(idPublicWork = publicWorkId, idUser = currentUser))
        mPhotoList.postValue(mutableListOf())
    }

    fun setPublicWork(publicWork: PublicWorkAndAdress) {
        viewModelScope.launch(Dispatchers.IO) {
            loadPublicWork(publicWork.publicWork.id)
            loadCollectFromPublicWork(publicWork.publicWork.id)
        }
    }

    @WorkerThread
    private fun loadPublicWork(publicWorkId: String) {
        mSelectedPublicWork =
            publicWorkRepository.getPublicWorkByIdLive(publicWorkId)
    }

    @WorkerThread
    private fun loadPhotoForCollect(collectId: String) {
        val photos = collectRepository.listPhotosByCollectionID(collectId)
        mPhotoList.postValue(photos.toMutableList())
    }

    @WorkerThread
    private fun loadCollectFromPublicWork(publicWorkId: String) {
        val collect = collectRepository.getCollectByPublicWork(publicWorkId)
        if (collect != null) {
            currentCollect.postValue(collect)
            loadPhotoForCollect(collect.id)
        } else {
            newCollect(publicWorkId)
        }
    }

    fun getPublicWork(): LiveData<PublicWorkAndAdress> = mSelectedPublicWork

    fun getPhotoList(): LiveData<MutableList<Photo>> = mPhotoList

    fun addPhoto(photo: Photo) {
        mPhotoList.value?.let {
            val currentCollect = currentCollect.value ?: return@let
            photo.idCollect = currentCollect.id
            it.add(photo)
            mPhotoList.value = it
        }
    }

    fun updateCollect() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = currentCollect.value ?: return@launch
            val photos = mPhotoList.value ?: return@launch
            collectRepository.insertCollect(collection, photos)
        }
    }

}