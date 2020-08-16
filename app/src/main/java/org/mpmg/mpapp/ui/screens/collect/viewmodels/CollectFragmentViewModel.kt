package org.mpmg.mpapp.ui.screens.collect.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectFragmentViewModel : ViewModel() {

    var rotated = MutableLiveData<Boolean>()

    init {
        rotated.value = false
    }

    fun rotateFabMenu(v: View) {
        rotated.value?.let {
            v.animate().setDuration(200).rotation(if (!it) 135f else 0f)
            rotated.value = !it
        }
    }
}