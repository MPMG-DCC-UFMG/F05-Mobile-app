package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class TypeWorkViewModel(private val typeWorkRepository: ITypeWorkRepository) : ViewModel() {

    private var typeOfWorkList = typeWorkRepository.listAllTypeWorksLive()

    fun getTypeOfWorkList() = typeOfWorkList

    fun getTypeOfWorkFromFlag(typeWorkFlag: Int) =
        typeOfWorkList.value?.first { it.flag == typeWorkFlag }
}