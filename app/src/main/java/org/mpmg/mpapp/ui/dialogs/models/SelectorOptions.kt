package org.mpmg.mpapp.ui.dialogs.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.mpmg.mpapp.core.interfaces.BaseModel
import androidx.databinding.library.baseAdapters.BR
import org.mpmg.mpapp.ui.dialogs.SelectorDialog

data class SelectorOptions(
    val value: String,
    private var mSelected: Boolean,
    val selectionMode: SelectorDialog.SelectionMode,
    var showDivider: Boolean = true,
    var onClick: ((position: Int) -> Unit)? = null
) : BaseObservable(), BaseModel {

    var selected: Boolean
        @Bindable get() = mSelected
        set(value) {
            mSelected = value
            notifyPropertyChanged(BR.selected)
        }
}