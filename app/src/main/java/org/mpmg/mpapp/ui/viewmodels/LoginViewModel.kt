package org.mpmg.mpapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.User
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository

class LoginViewModel(
    val applicationContext: Application,
    private val userRepository: IUserRepository,
    private val configRepository: IConfigRepository
) : AndroidViewModel(applicationContext) {

    fun checkGoogleSignedAccount(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        return account?.email?.let {
            logIn(it)
            true
        } ?: false
    }

    fun addUserToDb(account: GoogleSignInAccount?) {
        account ?: return
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(
                User(name = account.displayName ?: "", email = account.email ?: return@launch)
            )
        }
    }

    fun logIn(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.setLoggedUserEmail(userEmail)
        }
    }
}