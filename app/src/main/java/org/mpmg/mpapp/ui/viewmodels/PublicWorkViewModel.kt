package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class PublicWorkViewModel(
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private var publicWorkList: LiveData<List<PublicWork>>

    init {
        publicWorkList = publicWorkRepository.listAllPublicWorksLive()
    }

    fun getPublicWorkList() = publicWorkList
}