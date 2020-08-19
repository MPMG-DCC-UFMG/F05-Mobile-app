package org.mpmg.mpapp.ui.screens.login.models

import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class LoginUI(
    private var _email: String = "",
    private var _password: String = "",
    private var _valid: Boolean = false
) : BaseObservable() {
    var email: String
        @Bindable get() = _email
        set(value) {
            _email = value
            checkFormValid()
            notifyPropertyChanged(BR.email)
        }

    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
            checkFormValid()
            notifyPropertyChanged(BR.password)
        }

    var isValid: Boolean
        @Bindable get() = _valid
        private set(value) {
            _valid = value
            notifyPropertyChanged(BR.valid)
        }

    private fun checkFormValid() {
        isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
    }
}