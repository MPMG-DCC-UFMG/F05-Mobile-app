package org.mpmg.mpapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.ui.shared.models.RequestStatus


class LoginViewModel(
    val applicationContext: Application,
    private val userRepository: UserRepository,
    private val configRepository: ConfigRepository
) : AndroidViewModel(applicationContext) {

    fun checkUserLogged(): Boolean = checkFirebaseSignedAccount()

    val isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.value = false
    }

    private fun checkFirebaseSignedAccount(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        return firebaseUser?.email?.let {
            logIn(it)
            true
        } ?: false
    }

    fun addUserToDb(userName: String, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(
                User(
                    name = userName,
                    email = userEmail
                )
            )
        }
    }

    fun authWithMPServer(userName: String, password: String): LiveData<RequestStatus> {
        val status = MutableLiveData<RequestStatus>()
        status.value = RequestStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                userRepository.login(userName, password)
            }.onSuccess {
                status.postValue(RequestStatus.SUCCESS)
            }.onFailure {
                status.postValue(RequestStatus.FAILED)
            }
        }
        return status
    }

    fun logIn(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.setLoggedUserEmail(userEmail)
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}