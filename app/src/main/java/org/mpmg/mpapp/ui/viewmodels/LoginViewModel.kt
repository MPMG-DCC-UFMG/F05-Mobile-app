package org.mpmg.mpapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.User
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository

class LoginViewModel(
    val applicationContext: Application,
    private val userRepository: IUserRepository
) : AndroidViewModel(applicationContext) {


    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    fun checkGoogleSignedAccount(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        return account != null
    }

    fun addUserToDb(account: GoogleSignInAccount?) {
        account ?: return
        ioScope.launch {
            userRepository.insertUser(
                User(name = account.displayName ?: "", email = account.email ?: return@launch)
            )
        }
    }
}