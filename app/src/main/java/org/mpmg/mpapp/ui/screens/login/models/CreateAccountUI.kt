package org.mpmg.mpapp.ui.screens.login.models

import android.content.Context
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.mpmg.mpapp.R

data class CreateAccountUI(
    private var _email: String = "",
    private var _password1: String = "",
    private var _password2: String = "",
    private var _valid: Boolean = false,
    private var _invalidMessage: String = "",
    private val context: Context
) : BaseObservable() {

    var email: String
        @Bindable get() = _email
        set(value) {
            _email = value
            checkFormValid()
            notifyPropertyChanged(BR.email)
        }

    var password1: String
        @Bindable get() = _password1
        set(value) {
            _password1 = value
            checkFormValid()
            notifyPropertyChanged(BR.password1)
        }

    var password2: String
        @Bindable get() = _password2
        set(value) {
            _password2 = value
            checkFormValid()
            notifyPropertyChanged(BR.password2)
        }

    var isValid: Boolean
        @Bindable get() = _valid
        private set(value) {
            _valid = value
            notifyPropertyChanged(BR.valid)
        }

    var invalidMessage: String
        @Bindable get() = _invalidMessage
        set(value) {
            _invalidMessage = value
            notifyPropertyChanged(BR.invalidMessage)
        }

    private fun checkFormValid() {
        isValid = when {
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                invalidMessage = context.getString(R.string.error_create_account_email)
                false
            }
            password1 != password2 -> {
                invalidMessage = context.getString(R.string.error_create_account_password_different)
                false
            }
            password1.isEmpty() && password2.isEmpty() -> {
                invalidMessage = ""
                false
            }
            password1.isEmpty() || password2.isEmpty() -> {
                invalidMessage = context.getString(R.string.error_create_account_password_empty)
                false
            }
            else -> {
                invalidMessage = ""
                true
            }
        }
    }
}