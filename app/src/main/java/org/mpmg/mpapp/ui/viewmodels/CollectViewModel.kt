package org.mpmg.mpapp.ui.viewmodels

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository

class CollectViewModel(
    private val collectRepository: CollectRepository,
    private val configRepository: ConfigRepository,
    private val publicWorkRepository: PublicWorkRepository
) : ViewModel() {

    private var mSelectedPublicWork: LiveData<PublicWorkAndAddress> = MutableLiveData()
    private var mPhotoList = MutableLiveData<MutableMap<String, Photo>>()

    var comment = MutableLiveData<String>()

    private val currentCollect = MutableLiveData<Collect>()

    private fun newCollect(publicWorkId: String) {
        val currentUser = configRepository.getLoggedUserEmail()
        currentCollect.postValue(
            Collect(
                idPublicWork = publicWorkId,
                idUser = currentUser
            )
        )
        comment.postValue(null)
        mPhotoList.postValue(mutableMapOf())
    }

    fun updateCommentToCollect(comment: String) {
        currentCollect.value?.comments = comment
    }

    fun setPublicWork(publicWork: PublicWorkAndAddress) {
        viewModelScope.launch(Dispatchers.IO) {
            loadPublicWork(publicWork.publicWork.id)
            loadCollectFromPublicWork(publicWork.publicWork.id)
        }
    }

    @WorkerThread
    private fun loadPublicWork(publicWorkId: String) {
        mSelectedPublicWork =
            publicWorkRepository.getPublicWorkByIdLive(publicWorkId).asLiveData()
    }

    @WorkerThread
    private fun loadPhotoForCollect(collectId: String) {
        val photos = collectRepository.listPhotosByCollectionID(collectId)
        mPhotoList.postValue(photos.map { it.id to it }.toMap().toMutableMap())
    }

    @WorkerThread
    private fun loadCollectFromPublicWork(publicWorkId: String) {
        val collect = collectRepository.getCollectByPublicWork(publicWorkId)
        if (collect != null) {
            comment.postValue(collect.comments)
            currentCollect.postValue(collect)
            loadPhotoForCollect(collect.id)
        } else {
            newCollect(publicWorkId)
        }
    }

    fun getPublicWork(): LiveData<PublicWorkAndAddress> = mSelectedPublicWork


    fun addPhoto(photo: Photo) {
        mPhotoList.value?.let {
            val currentCollect = currentCollect.value ?: return@let
            photo.idCollect = currentCollect.id
            it[photo.id] = photo
            mPhotoList.value = it
        }
    }

    fun deletePhoto(photo: Photo) {
        mPhotoList.value?.let {
            it.remove(photo.id)
            collectRepository.deletePhotoById(photoId = photo.id)
            mPhotoList.value = it
        }
    }

    fun updatePublicWorkStatus(workStatusFlag: Int) {
        currentCollect.value?.let {
            it.publicWorkStatus = workStatusFlag
        }
    }

    fun updateCollect() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = currentCollect.value ?: return@launch
            val photos = mPhotoList.value ?: return@launch
            val currentPublicWorkAndAddress = mSelectedPublicWork.value ?: return@launch

            if (photos.isNotEmpty()) {
                collectRepository.insertCollect(collection, photos.values.toList())
                currentPublicWorkAndAddress.publicWork.idCollect = collection.id
            }
            publicWorkRepository.insertPublicWork(
                currentPublicWorkAndAddress.publicWork,
                currentPublicWorkAndAddress.address
            )
        }
    }

    fun deleteCurrentCollect() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = currentCollect.value ?: return@launch
            collectRepository.deleteCollectById(collection.id)

            val currentPublicWorkAndAddress = mSelectedPublicWork.value ?: return@launch
            publicWorkRepository.unlinkCollectFromPublicWork(currentPublicWorkAndAddress.publicWork.id)
        }
    }

}