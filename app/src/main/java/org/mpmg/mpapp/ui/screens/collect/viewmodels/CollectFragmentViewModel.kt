package org.mpmg.mpapp.ui.screens.collect.viewmodels

import android.view.View
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.photo.models.PhotoActions.*
import org.mpmg.mpapp.ui.screens.photo.models.PhotoBundle
import org.mpmg.mpapp.ui.shared.animation.AnimationHelper

class CollectFragmentViewModel(
    private val publicWorkRepository: PublicWorkRepository,
    private val collectRepository: CollectRepository,
    private val configRepository: ConfigRepository,
    private val workStatusRepository: WorkStatusRepository,
    private val typeWorkRepository: TypeWorkRepository
) :
    MVVMViewModel() {

    var rotated = MutableLiveData<Boolean>()

    private val _selectedPublicWork = MutableLiveData<PublicWorkAndAddress>()
    val selectedPublicWork: LiveData<PublicWorkAndAddress> = _selectedPublicWork

    private var _photoList = MutableLiveData<MutableMap<String, Photo>>()
    val photoList: LiveData<MutableMap<String, Photo>> = _photoList

    val workStatuses = MutableLiveData<List<WorkStatus>>()

    val comment = MutableLiveData<String>()

    val navigateBack = SingleLiveEvent<Boolean>()

    private val _currentCollect = MutableLiveData<Collect>()
    val currentCollect: LiveData<Collect> = _currentCollect

    init {
        rotated.value = false
    }

    fun rotateFabMenu(v: View) {
        rotated.value?.let {
            AnimationHelper.rotate(v, if (!it) 135f else 0f)
            rotated.value = !it
        }
    }

    fun initMiniFABs(v: View) {
        v.visibility = View.GONE
        v.alpha = 0f
    }

    fun startCollectForPublicWork(publicWorkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadPublicWorkById((publicWorkId))
            if (currentCollect.value?.idPublicWork != publicWorkId) {
                loadCollectFromPublicWork(publicWorkId)
            }
        }
    }

    fun processPhotoBundle(photoBundle: PhotoBundle) {
        when (photoBundle.action) {
            ADD -> addPhoto(photoBundle.photo)
            DELETE -> deletePhoto(photoBundle.photo)
        }
    }

    @WorkerThread
    private fun loadPublicWorkById(publicWorkId: String) {
        val publicWork = publicWorkRepository.getPublicWorkById(publicWorkId)
        publicWork?.let {
            _selectedPublicWork.postValue(publicWork)
        }
    }

    fun publicWorkLoaded(publicWork: PublicWork) {
        viewModelScope.launch(Dispatchers.IO) {
            val typeWork =
                typeWorkRepository.getTypeOfWorkFromFlag(publicWork.typeWorkFlag)
            workStatuses.postValue(workStatusRepository.listWorkStatusByIds(typeWork.getWorkStatusIds()))
        }
    }

    fun updatePublicWorkStatus(workStatusFlag: Int) {
        _currentCollect.value?.let {
            it.publicWorkStatus = workStatusFlag
        }
    }

    fun updateCollect() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = _currentCollect.value ?: return@launch
            val photos = photoList.value ?: return@launch
            val currentPublicWorkAndAddress = selectedPublicWork.value ?: return@launch

            if (photos.isNotEmpty()) {
                collectRepository.insertCollect(collection, photos.values.toList())
                currentPublicWorkAndAddress.publicWork.idCollect = collection.id
            }
            publicWorkRepository.insertPublicWork(
                currentPublicWorkAndAddress.publicWork,
                currentPublicWorkAndAddress.address
            )

            navigateBack(true)
        }
    }

    @WorkerThread
    private fun loadCollectFromPublicWork(publicWorkId: String) {
        val collect = collectRepository.getCollectByPublicWork(publicWorkId)
        if (collect != null) {
            comment.postValue(collect.comments)
            _currentCollect.postValue(collect)
            loadPhotoForCollect(collect.id)
        } else {
            newCollect(publicWorkId)
        }
    }

    private fun newCollect(publicWorkId: String) {
        val currentUser = configRepository.getLoggedUserEmail()
        _currentCollect.postValue(
            Collect(
                idPublicWork = publicWorkId,
                idUser = currentUser
            )
        )
        comment.postValue(null)
        _photoList.postValue(mutableMapOf())
    }

    @WorkerThread
    private fun loadPhotoForCollect(collectId: String) {
        val photos = collectRepository.listPhotosByCollectionID(collectId)
        _photoList.postValue(photos.map { it.id to it }.toMap().toMutableMap())
    }

    fun navigateBack(updated: Boolean) {
        navigateBack.postValue(updated)
    }

    fun deleteCurrentCollect() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = _currentCollect.value ?: return@launch
            collectRepository.deleteCollectById(collection.id)

            val currentPublicWorkAndAddress = selectedPublicWork.value ?: return@launch
            publicWorkRepository.unlinkCollectFromPublicWork(currentPublicWorkAndAddress.publicWork.id)
            navigateBack(false)
        }
    }

    fun updateCommentToCollect(comment: String) {
        _currentCollect.value?.comments = comment
    }

    private fun addPhoto(photo: Photo) {
        _photoList.value?.let {
            val currentCollect = currentCollect.value ?: return@let
            photo.idCollect = currentCollect.id
            it[photo.id] = photo
            _photoList.postValue(it)
        }
    }

    private fun deletePhoto(photo: Photo) {
        _photoList.value?.let {
            it.remove(photo.id)
            collectRepository.deletePhotoById(photoId = photo.id)
            _photoList.postValue(it)
        }
    }

}