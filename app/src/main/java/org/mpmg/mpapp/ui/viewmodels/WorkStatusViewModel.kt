package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository

class WorkStatusViewModel(private val workStatusRepository: WorkStatusRepository) : ViewModel() {

    val currentWorkStatusList = MutableLiveData<List<WorkStatus>>()

    fun loadWorkStatusFromList(workStatusIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            currentWorkStatusList.postValue(workStatusRepository.listWorkStatusByIds(workStatusIds))
        }
    }
}