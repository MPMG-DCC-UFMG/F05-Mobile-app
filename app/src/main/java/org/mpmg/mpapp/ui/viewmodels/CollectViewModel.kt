package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

class CollectViewModel : ViewModel() {

    private var selectedPublicWork = MutableLiveData<PublicWorkAndAdress>()

    fun setPublicWork(publicWork: PublicWorkAndAdress) {
        selectedPublicWork.value = publicWork
    }

    fun getPublicWork() = selectedPublicWork
}