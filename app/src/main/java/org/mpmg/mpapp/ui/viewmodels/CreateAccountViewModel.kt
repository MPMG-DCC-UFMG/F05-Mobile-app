package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.ui.screens.login.models.CreateAccountUI
import org.mpmg.mpapp.ui.shared.models.RequestStatus
import org.mpmg.mpapp.ui.shared.models.RequestStatus.*

class CreateAccountViewModel(private val userRepository: IUserRepository) : ViewModel() {

    val status = MutableLiveData<RequestStatus>()

    init {
        status.value = NOT_STARTED
    }

    fun createUser(createAccountUI: CreateAccountUI) {
        status.value = LOADING
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { userRepository.createUser(MPUserRemote(createAccountUI)) }
                .onSuccess {
                    if (it.success) {
                        status.postValue(SUCCESS)
                    } else {
                        status.postValue(FAILED)
                    }
                }
                .onFailure { status.postValue(FAILED) }
        }
    }
}