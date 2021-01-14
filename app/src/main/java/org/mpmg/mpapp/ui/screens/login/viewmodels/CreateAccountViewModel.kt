package org.mpmg.mpapp.ui.screens.login.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.domain.network.models.MPUserRemote
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.login.models.CreateAccountUI
import org.mpmg.mpapp.ui.shared.models.RequestStatus
import org.mpmg.mpapp.ui.shared.models.RequestStatus.*

class CreateAccountViewModel(private val userRepository: UserRepository) : MVVMViewModel() {

    val userCreated = SingleLiveEvent<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    lateinit var createAccountUI: CreateAccountUI

    fun startAccountUI(context: Context) {
        createAccountUI = CreateAccountUI(context = context)
    }

    fun createUser() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { userRepository.createUser(MPUserRemote(createAccountUI)) }
                .onSuccess {
                    isLoading.postValue(false)
                    userCreated.postValue(it.success)
                    if (!it.success) {
                        snackMessage.postValue(it.error?.message)
                        createAccountUI.invalidMessage =
                            it.error?.message ?: "Erro ao criar conta do usuário"
                    }
                }
                .onFailure {
                    isLoading.postValue(false)
                    snackMessage.postValue("Erro ao comunicar com o servidor")
                }
        }
    }
}