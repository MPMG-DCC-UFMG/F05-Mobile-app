package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel

class LocationViewModel : MVVMViewModel() {

    private val mCurrentLocation = MutableLiveData<Location>()

    val geocodingAddress = SingleLiveEvent<Address>()
    val currentAddress = MutableLiveData<Address>()
    val selectedLocation = SingleLiveEvent<LatLng>()

    fun updateCurrentLocation(location: Location) {
        mCurrentLocation.value = location
    }

    fun setSelectedLocation(location: LatLng) {
        selectedLocation.postValue(location)
    }

    fun getCurrentLocationLiveData() = mCurrentLocation

    fun getCurrentLocation() = mCurrentLocation.value

    fun searchAddress(context: Context, latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val addressList =
                Geocoder(context).getFromLocation(latLng.latitude, latLng.longitude, 1)
            val address = addressList[0]
            address.latitude = latLng.latitude
            address.longitude = latLng.longitude
            geocodingAddress.postValue(address)
            currentAddress.postValue(address)
        }
    }

    fun cleanLocations() {
        geocodingAddress.postValue(null)
        selectedLocation.postValue(null)
        cleanCurrentAddress()
    }

    fun cleanCurrentAddress() {
        currentAddress.postValue(null)
    }
}