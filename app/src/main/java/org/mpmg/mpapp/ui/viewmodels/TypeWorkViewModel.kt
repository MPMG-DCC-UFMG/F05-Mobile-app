package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository

class TypeWorkViewModel(private val typeWorkRepository: TypeWorkRepository) : ViewModel() {

    private lateinit var typeOfWorkList: List<TypeWork>

    fun getTypeOfWorkList() = typeOfWorkList

    fun refreshTypeWorkList() {
        viewModelScope.launch(Dispatchers.IO) {
            typeOfWorkList = typeWorkRepository.listAllTypeWorks()
        }
    }

    fun getTypeOfWorkFromFlag(typeWorkFlag: Int) =
        typeOfWorkList.first { it.flag == typeWorkFlag }
}