package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class HomeViewModel(private val publicWorkRepository: IPublicWorkRepository) : ViewModel() {

    val countToSendLive = publicWorkRepository.countPublicWorkToSendLive()
}