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

class LocationViewModel : ViewModel() {

    private val mCurrentLocation = MutableLiveData<Location>()

    var geocodingAddress = MutableLiveData<Address>()

    fun updateCurrentLocation(location: Location) {
        mCurrentLocation.value = location
    }

    fun getCurrentLocationLiveData() = mCurrentLocation

    fun getCurrentLocation() = mCurrentLocation.value

    fun searchAddress(context: Context, latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val address =
                Geocoder(context).getFromLocation(latLng.latitude, latLng.longitude, 1)
            geocodingAddress.postValue(address[0])
        }
    }

    fun clearAddress() {
        geocodingAddress.postValue(null)
    }
}