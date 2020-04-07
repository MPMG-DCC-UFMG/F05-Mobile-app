package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository

class PublicWorkViewModel(
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private var publicWorkList: LiveData<List<PublicWorkAndAdress>> = publicWorkRepository.listAllPublicWorksLive()
    private val _currentPublicWorkAdress = MutableLiveData<PublicWorkAndAdress>()
    private val _currentTypeWork = MutableLiveData<TypeWork>()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    val currentPublicWorkAndAddress: LiveData<PublicWorkAndAdress> = _currentPublicWorkAdress
    val currentTypeWork: LiveData<TypeWork> = _currentTypeWork

    init {
        newCurrentPublicWork()
    }

    private fun newCurrentPublicWork() {
        val publicWork = PublicWork()
        _currentPublicWorkAdress.postValue(
            PublicWorkAndAdress(
                publicWork = publicWork,
                address = Address(idPublicWork = publicWork.id)
            )
        )
    }

    fun setCurrentTypeWork(typeWork: TypeWork) {
        _currentTypeWork.value = typeWork
        _currentPublicWorkAdress.value?.let {
            it.publicWork.typeWorkFlag = typeWork.flag
            _currentPublicWorkAdress.value = it
        }
    }

    fun getPublicWorkList() = publicWorkList

    fun addPublicWork() {
        ioScope.launch {
            currentPublicWorkAndAddress.value?.let {
                publicWorkRepository.insertPublicWork(it.publicWork, it.address)
                newCurrentPublicWork()
            }
        }
    }
}