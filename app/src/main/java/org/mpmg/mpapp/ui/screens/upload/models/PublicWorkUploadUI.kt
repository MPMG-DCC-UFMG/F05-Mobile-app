package org.mpmg.mpapp.ui.screens.upload.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.mpmg.mpapp.core.interfaces.BaseModel
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.*

data class PublicWorkUploadUI(
    val name: String,
    val id: String,
    val idCollect: String?,
    val toSend: Boolean,
    private var _status: String,
    private var _progress: Int = 0,
    private var _workState: WorkInfo.State,
    val workerInfoId: MutableLiveData<UUID> = MutableLiveData<UUID>()
) : BaseModel, BaseObservable() {

    var status: String
        @Bindable get() = _status
        set(value) {
            _status = value
            notifyPropertyChanged(BR.status)
        }

    var workState: WorkInfo.State
        @Bindable get() = _workState
        set(value) {
            _workState = value
            notifyPropertyChanged(BR.workState)
        }

    var progress: Int
        @Bindable get() = _progress
        set(value) {
            _progress = value
            notifyPropertyChanged(BR.progress)
        }


}