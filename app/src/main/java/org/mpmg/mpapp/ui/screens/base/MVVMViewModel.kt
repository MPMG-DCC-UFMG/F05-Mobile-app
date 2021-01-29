package org.mpmg.mpapp.ui.screens.base

import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.core.events.SingleLiveEvent

abstract class MVVMViewModel : ViewModel() {

    val snackMessage = SingleLiveEvent<String>()

    fun launchSnackbar(message: String) {
        snackMessage.postValue(message)
    }
}