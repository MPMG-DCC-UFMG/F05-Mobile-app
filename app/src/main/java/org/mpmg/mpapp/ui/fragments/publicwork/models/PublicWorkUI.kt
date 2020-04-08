package org.mpmg.mpapp.ui.fragments.publicwork.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.mpmg.mpapp.domain.models.PublicWork

data class PublicWorkUI(
    private var _name: String = ""
) : BaseObservable() {

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    fun isValid() = this.name.isNotBlank()

    fun toPublicWorkDB(): PublicWork {
        return PublicWork(
            name = this.name
        )
    }
}