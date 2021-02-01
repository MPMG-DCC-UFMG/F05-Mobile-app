package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.publicwork.models.MapClusterItem

class PublicWorkMapViewModel : MVVMViewModel() {

    private val _mapPublicWorkList = MutableLiveData<List<MapClusterItem>>()
    val mapPublicWorkList: LiveData<List<MapClusterItem>> = _mapPublicWorkList

    fun applyPublicWorkList(publicWorkList: List<PublicWorkAndAddress>) {
        viewModelScope.launch(Dispatchers.IO) {
            val filtered = publicWorkList.filter { it.address.getLatLng() != null }
            _mapPublicWorkList.postValue(filtered.map {
                MapClusterItem(
                    publicWorkAndAddress = it,
                    location = it.address.getLatLng()!!
                )
            })
        }
    }
}