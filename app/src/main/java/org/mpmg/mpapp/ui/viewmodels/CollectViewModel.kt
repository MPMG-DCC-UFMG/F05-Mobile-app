package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.domain.models.Collect
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

class CollectViewModel : ViewModel() {

    private val mSelectedPublicWork = MutableLiveData<PublicWorkAndAdress>()
    private val mPhotoMap = MutableLiveData<HashMap<String, Photo>>()

    private val currentCollection = MutableLiveData<Collect>()

    init {
        newCollect()
    }

    private fun newCollect() {
        currentCollection.value = Collect()
        mPhotoMap.value = hashMapOf()
    }

    fun setPublicWork(publicWork: PublicWorkAndAdress) {
        newCollect()
        mSelectedPublicWork.value = publicWork
        currentCollection.value?.idPublicWork = publicWork.publicWork.id
    }

    fun getPublicWork(): LiveData<PublicWorkAndAdress> = mSelectedPublicWork

    fun getPhotoMap(): LiveData<HashMap<String, Photo>> = mPhotoMap

    fun addPhoto(photo: Photo) {
        mPhotoMap.value?.let {
            it[photo.id] = photo
            mPhotoMap.value = it
        }
    }

}