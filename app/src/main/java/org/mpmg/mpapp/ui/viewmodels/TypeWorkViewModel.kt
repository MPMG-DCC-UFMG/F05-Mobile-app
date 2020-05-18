package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository

class TypeWorkViewModel(private val typeWorkRepository: ITypeWorkRepository) : ViewModel() {

    private var typeOfWorkList = MutableLiveData<List<TypeWork>>()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    init {
        ioScope.launch {
            typeOfWorkList.postValue(typeWorkRepository.listAllTypeWorks())
        }
    }

    fun getTypeOfWorkList() = typeOfWorkList

    fun getTypeOfWorkFromFlag(typeWorkFlag: Int) =
        typeOfWorkList.value?.first { it.flag == typeWorkFlag }
}