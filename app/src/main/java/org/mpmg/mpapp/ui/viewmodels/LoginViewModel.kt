package org.mpmg.mpapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn

class LoginViewModel(val applicationContext: Application) : AndroidViewModel(applicationContext) {

    fun checkGoogleSignedAccount(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        return account != null
    }


}