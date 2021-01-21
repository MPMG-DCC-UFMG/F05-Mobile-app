package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import android.location.Address
import android.location.Location
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.repositories.city.CityRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.publicwork.models.AddressUI
import org.mpmg.mpapp.ui.screens.publicwork.models.PublicWorkUI

class AddPublicWorkViewModel(
    private val publicWorkRepository: PublicWorkRepository,
    private val typeWorkRepository: TypeWorkRepository,
    private val cityRepository: CityRepository
) :
    MVVMViewModel() {

    private val TAG = AddPublicWorkViewModel::class.java.name

    lateinit var currentPublicWork: PublicWorkUI
    lateinit var currentAddress: AddressUI

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

    private fun newCurrentPublicWorkAddress() {
        val publicWork = PublicWorkUI()
        val address = AddressUI()
        isNewPublicWork.value = true

        updateCurrentPublicWorkAddress(publicWork, address)
    }

    private fun updateCurrentPublicWorkAddress(publicWorkUI: PublicWorkUI, addressUI: AddressUI) {
        publicWorkUI.addOnPropertyChangedCallback(observableCallback)
        addressUI.addOnPropertyChangedCallback(observableCallback)

        currentPublicWork = publicWorkUI
        currentAddress = addressUI
    }

    fun isFormValid(): Boolean {
        return currentAddress.isValid() && currentPublicWork.isValid()
    }

    fun isLocationValid(): Boolean {
        return currentAddress.isLocationValid()
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
        val publicWork = currentPublicWork.toPublicWorkDB()
        val address = currentAddress.toAddressDB()

        viewModelScope.launch(Dispatchers.IO) {
            val typeWork = currentTypeWork.value ?: return@launch

            address.idPublicWork = publicWork.id
            publicWork.typeWorkFlag = typeWork.flag

            publicWorkRepository.insertPublicWork(publicWork, address)
        }
    }

    fun updateCurrPublicWorkLocation(location: LatLng) {
        currentAddress.apply {
            latitude = location.latitude
            longitude = location.longitude
        }
    }

    fun fromGeocoding(address: Address) {
        currentAddress.city = address.subAdminArea ?: ""
        currentAddress.street = address.thoroughfare ?: ""
        currentAddress.neighborhood = address.subLocality ?: ""
        currentAddress.number = address.subThoroughfare ?: ""
        currentAddress.cep = address.postalCode ?: ""
        currentAddress.latitude = address.latitude
        currentAddress.longitude = address.longitude
    }
}