package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import android.location.Address
import android.location.Location
import androidx.annotation.WorkerThread
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.city.CityRepository
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.publicwork.models.AddressUI
import org.mpmg.mpapp.ui.screens.publicwork.models.PublicWorkUI

class CrudPublicWorkViewModel(
    private val publicWorkRepository: PublicWorkRepository,
    private val typeWorkRepository: TypeWorkRepository,
    cityRepository: CityRepository
) :
    MVVMViewModel() {

    private val TAG = CrudPublicWorkViewModel::class.java.name

    val currentPublicWork = MutableLiveData<PublicWorkUI>()
    val currentAddress = MutableLiveData<AddressUI>()

    val currentTypeWork: MutableLiveData<TypeWork> = MutableLiveData<TypeWork>()
    val isPublicWorkValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val query: MutableLiveData<String> = MutableLiveData<String>()
    val isNewPublicWork: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    var typeWorkList = typeWorkRepository.listAllTypeWorksLive().asLiveData()
    var citiesList = cityRepository.listCitiesLive().asLiveData()

    private var currentLocation: Location? = null

    private val observableCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            isPublicWorkValid.value = isFormValid()
        }
    }

    init {
        newCurrentPublicWorkAddress()
    }

    fun startPublicWork(publicWorkId: String?) {
        if (publicWorkId != null) {
            applyEditMode(publicWorkId)
        }
    }

    private fun newCurrentPublicWorkAddress() {
        val publicWork = PublicWorkUI()
        val address = AddressUI()
        isNewPublicWork.value = true

        updateCurrentPublicWorkAddress(publicWork, address)
    }

    private fun applyEditMode(publicWorkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            publicWorkRepository.getPublicWorkById(publicWorkId)?.let { publicWorkAndAddress ->
                val publicWorkUI = PublicWorkUI(publicWorkAndAddress.publicWork)
                val addressUI = AddressUI(publicWorkAndAddress.address)
                isNewPublicWork.postValue(false)
                updateTypeWorkByFlag(publicWorkAndAddress.publicWork.typeWorkFlag)

                updateCurrentPublicWorkAddress(publicWorkUI, addressUI)
            }
        }
    }

    @WorkerThread
    private fun updateTypeWorkByFlag(typeWorkFlag: Int) {
        val typeWork = typeWorkRepository.getTypeOfWorkFromFlag(typeWorkFlag)
        currentTypeWork.postValue(typeWork)
    }

    private fun updateCurrentPublicWorkAddress(publicWorkUI: PublicWorkUI, addressUI: AddressUI) {
        publicWorkUI.addOnPropertyChangedCallback(observableCallback)
        addressUI.addOnPropertyChangedCallback(observableCallback)

        currentPublicWork.postValue(publicWorkUI)
        currentAddress.postValue(addressUI)
    }

    fun isFormValid(): Boolean {
        return currentAddress.value?.isValid() ?: false && currentPublicWork.value?.isValid() ?: false
    }

    fun isLocationValid(): Boolean {
        return currentAddress.value?.isLocationValid() ?: false
    }

    fun setInitialTypeWork(typeWork: TypeWork) {
        if (currentTypeWork.value == null) {
            currentTypeWork.value = typeWork
        }
    }

    fun setCurrentTypeWork(typeWork: TypeWork?) {
        currentTypeWork.value = typeWork
    }

    fun addPublicWork() {
        val publicWork = currentPublicWork.value?.toPublicWorkDB() ?: return
        val address = currentAddress.value?.toAddressDB() ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val typeWork = currentTypeWork.value ?: return@launch

            address.idPublicWork = publicWork.id
            publicWork.typeWorkFlag = typeWork.flag

            publicWorkRepository.insertPublicWork(publicWork, address)
        }
    }

    fun updateCurrPublicWorkLocation(location: LatLng) {
        val address = currentAddress.value ?: return
        address.apply {
            latitude = location.latitude
            longitude = location.longitude
        }
        currentAddress.postValue(address)
    }

    fun fromGeocoding(address: Address) {
        val currAddress = currentAddress.value ?: return
        currAddress.city = address.subAdminArea ?: ""
        currAddress.street = address.thoroughfare ?: ""
        currAddress.neighborhood = address.subLocality ?: ""
        currAddress.number = address.subThoroughfare ?: ""
        currAddress.cep = address.postalCode ?: ""
        currAddress.latitude = address.latitude
        currAddress.longitude = address.longitude
        currentAddress.postValue(currAddress)
    }
}