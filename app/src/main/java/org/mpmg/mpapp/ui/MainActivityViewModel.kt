package org.mpmg.mpapp.ui

import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.core.events.SingleLiveEvent

class MainActivityViewModel : ViewModel() {

    val snackbarMessage = SingleLiveEvent<String>()

    fun launchSnackBar(message: String) {
        snackbarMessage.postValue(message)
    }
}