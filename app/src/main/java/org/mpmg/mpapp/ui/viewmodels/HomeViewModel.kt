package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository

class HomeViewModel(private val publicWorkRepository: PublicWorkRepository) : ViewModel() {

    val countToSendLive = publicWorkRepository.countPublicWorkToSendLive().asLiveData()
}