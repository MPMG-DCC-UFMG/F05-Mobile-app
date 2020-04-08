package org.mpmg.mpapp.ui.viewmodels

import android.location.Location
import androidx.databinding.Observable
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.ui.fragments.publicwork.models.AddressUI
import org.mpmg.mpapp.ui.fragments.publicwork.models.PublicWorkUI

class PublicWorkViewModel(
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private val TAG = PublicWorkViewModel::class.java.name

    private var publicWorkList: LiveData<List<PublicWorkAndAdress>> =
        publicWorkRepository.listAllPublicWorksLive()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    lateinit var currentPublicWork: PublicWorkUI
    lateinit var currentAddress: AddressUI

    val currentTypeWork: MutableLiveData<TypeWork> = MutableLiveData<TypeWork>()

    val isPublicWorkValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val observableCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            isPublicWorkValid.value = isFormValid()
        }
    }

    init {
        newCurrentPublicWorkAddress()
    }

    private fun newCurrentPublicWorkAddress() {
        val publicWork = PublicWorkUI()
        val address = AddressUI()

        publicWork.addOnPropertyChangedCallback(observableCallback)
        address.addOnPropertyChangedCallback(observableCallback)

        currentPublicWork = publicWork
        currentAddress = address
    }

    fun isFormValid(): Boolean {
        return currentAddress.isValid() && currentPublicWork.isValid()
    }

    fun setCurrentTypeWork(typeWork: TypeWork) {
        currentTypeWork.value = typeWork
    }

    fun getPublicWorkList() = publicWorkList

    fun addPublicWork() {
        val publicWork = currentPublicWork.toPublicWorkDB()
        val address = currentAddress.toAddressDB()

        ioScope.launch {
            val typeWork = currentTypeWork.value ?: return@launch

            address.idPublicWork = publicWork.id
            publicWork.typeWorkFlag = typeWork.flag

            publicWorkRepository.insertPublicWork(publicWork, address)
        }
    }

    fun updateCurrPublicWorkLocation(location: Location) {
        currentAddress.apply {
            latitude = location.latitude
            longitude = location.longitude
        }
    }
}