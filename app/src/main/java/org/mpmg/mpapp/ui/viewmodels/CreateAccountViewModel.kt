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

    val status = MutableLiveData<Pair<RequestStatus,String?>>()

    init {
        status.value = Pair(NOT_STARTED,null)
    }

    fun createUser(createAccountUI: CreateAccountUI) {
        status.value = Pair(LOADING,null)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { userRepository.createUser(MPUserRemote(createAccountUI)) }
                .onSuccess {
                    if (it.success) {
                        status.postValue(Pair(SUCCESS,null))
                    } else {
                        status.postValue(Pair(FAILED, it.error?.message))
                    }
                }
                .onFailure { status.postValue(Pair(FAILED,"Erro ao comunicar com o servidor")) }
        }
    }
}