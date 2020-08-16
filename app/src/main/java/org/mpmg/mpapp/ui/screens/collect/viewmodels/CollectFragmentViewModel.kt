package org.mpmg.mpapp.ui.screens.collect.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.ui.shared.animation.AnimationHelper

class CollectFragmentViewModel : ViewModel() {

    var rotated = MutableLiveData<Boolean>()

    init {
        rotated.value = false
    }

    fun rotateFabMenu(v: View) {
        rotated.value?.let {
            AnimationHelper.rotate(v, if (!it) 135f else 0f)
            rotated.value = !it
        }
    }

    fun initMiniFABs(v:View){
        v.visibility = View.GONE
        v.alpha = 0f
    }
}