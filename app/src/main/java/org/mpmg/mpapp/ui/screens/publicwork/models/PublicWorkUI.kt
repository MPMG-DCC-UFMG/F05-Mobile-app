package org.mpmg.mpapp.ui.screens.publicwork.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.mpmg.mpapp.domain.database.models.PublicWork

data class PublicWorkUI(
    var id: String? = null,
    private var _name: String = ""
) : BaseObservable() {

    constructor(publicWork: PublicWork) : this() {
        id = publicWork.id
        _name = publicWork.name
    }

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    fun isValid() = this.name.isNotBlank()

    fun toPublicWorkDB(): PublicWork {
        val publicWorkDB = PublicWork(
            name = this.name,
            toSend = true
        )

        id?.let { publicWorkDB.id = it }

        return publicWorkDB
    }
}