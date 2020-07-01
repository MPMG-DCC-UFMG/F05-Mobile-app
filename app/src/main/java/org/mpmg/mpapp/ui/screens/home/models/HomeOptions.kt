package org.mpmg.mpapp.ui.screens.home.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.mpmg.mpapp.core.interfaces.BaseModel
import androidx.databinding.library.baseAdapters.BR


data class HomeOptions(
    val iconResourceId: Int,
    val optionTitle: String,
    private var mEventCount: Int = 0,
    var onClick: (() -> Unit)? = null
) : BaseObservable(), BaseModel {

    var eventCount : Int
    @Bindable get() = mEventCount
    set(value) {
        mEventCount = value
        notifyPropertyChanged(BR.eventCount)
    }

}