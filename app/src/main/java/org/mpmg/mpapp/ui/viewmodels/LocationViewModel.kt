package org.mpmg.mpapp.ui.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val mCurrentLocation = MutableLiveData<Location>()

    fun updateCurrentLocation(location: Location) {
        mCurrentLocation.value = location
    }

    fun getCurrentLocationLiveData() = mCurrentLocation

    fun getCurrentLocation() = mCurrentLocation.value
}