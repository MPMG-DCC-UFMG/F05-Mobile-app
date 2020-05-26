package org.mpmg.mpapp.ui.viewmodels

import android.location.Address
import android.location.Location
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.ui.fragments.publicwork.models.AddressUI
import org.mpmg.mpapp.ui.fragments.publicwork.models.PublicWorkUI
import org.mpmg.mpapp.ui.shared.filters.*
import org.mpmg.mpapp.R

class PublicWorkViewModel(
    private val publicWorkRepository: IPublicWorkRepository
) : ViewModel() {

    private val TAG = PublicWorkViewModel::class.java.name

    private var publicWorkList: LiveData<List<PublicWorkAndAddress>> =
        publicWorkRepository.listAllPublicWorksLive()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    private val filterManager = PublicWorkFilterManager()
    private val filterSyncStatus = FilterSyncStatus()
    private val filterTypeWork = FilterTypeOfWork()
    private val filterByName = FilterByName()

    lateinit var currentPublicWork: PublicWorkUI
    lateinit var currentAddress: AddressUI

    val currentTypeWork: MutableLiveData<TypeWork> = MutableLiveData<TypeWork>()
    val isPublicWorkValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val query: MutableLiveData<String> = MutableLiveData<String>()
    val sortedCheckedId: MutableLiveData<Int> = MutableLiveData<Int>()
    val isNewPublicWork: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private var currentLocation: Location? = null

    private val publicWorkMediatedList = MediatorLiveData<List<PublicWorkAndAddress>>()

    private val observableCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            isPublicWorkValid.value = isFormValid()
        }
    }

    init {
        newCurrentPublicWorkAddress()
        filterManager.addFilter(filterSyncStatus.filterKey, filterSyncStatus)
        filterManager.addFilter(filterTypeWork.filterKey, filterTypeWork)
        filterManager.addFilter(filterByName.filterKey, filterByName)
        sortedCheckedId.postValue(R.id.radioButton_filterFragment_a_z)
        initMediator()
    }

    private fun initMediator() {
        publicWorkMediatedList.addSource(publicWorkList) { result ->
            result?.let {
                publicWorkMediatedList.value = filterManager.filter(it)
            }
        }

        publicWorkMediatedList.addSource(query) { search ->
            filterByName.query = search ?: ""
            publicWorkList.value?.let {
                publicWorkMediatedList.value = filterManager.filter(it)
            }
        }

        publicWorkMediatedList.addSource(sortedCheckedId) {
            sortList()
        }
    }

    fun newCurrentPublicWorkAddress() {
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

    fun setCurrentPublicWorkAddress(publicWorkAndAddress: PublicWorkAndAddress) {
        val publicWorkUI = PublicWorkUI(publicWorkAndAddress.publicWork)
        val addressUI = AddressUI(publicWorkAndAddress.address)
        isNewPublicWork.value = false

        updateCurrentPublicWorkAddress(publicWorkUI, addressUI)
    }

    fun isFormValid(): Boolean {
        return currentAddress.isValid() && currentPublicWork.isValid()
    }

    fun isLocationValid(): Boolean {
        return currentAddress.isLocationValid()
    }

    fun setInitialTypeWork(typeWork: TypeWork) {
        if(currentTypeWork.value == null){
            currentTypeWork.value = typeWork
        }
    }

    fun setCurrentTypeWork(typeWork: TypeWork?) {
        currentTypeWork.value = typeWork
    }

    fun getPublicWorkList() = publicWorkMediatedList

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

    fun updateCurrPublicWorkLocation(location: LatLng) {
        currentAddress.apply {
            latitude = location.latitude
            longitude = location.longitude
        }
    }

    fun updateSyncStatusFilter(syncStatus: SyncStatus, checked: Boolean) {
        if (checked) {
            filterSyncStatus.addSyncStatus(syncStatus)
        } else {
            filterSyncStatus.removeSyncStatus(syncStatus)
        }
        filter()
    }

    fun updateTypeWorkFilter(typeWorkFlag: Int, checked: Boolean) {
        if (checked) {
            filterTypeWork.addTypeOfWork(typeWorkFlag)
        } else {
            filterTypeWork.removeTypeOfWork(typeWorkFlag)
        }
        filter()
    }

    fun isSyncStatusChecked(syncStatus: SyncStatus): Boolean {
        return filterSyncStatus.isStatusEnabled(syncStatus)
    }

    fun getFilteredTypeOfWorks(options: Array<Int>): BooleanArray {
        val isCheckedOption = mutableListOf<Boolean>()
        options.forEach { option ->
            isCheckedOption.add(filterTypeWork.isTypeOfWorkChecked(option))
        }

        return isCheckedOption.toBooleanArray()
    }

    private fun filter() {
        publicWorkList.value?.let {
            publicWorkMediatedList.postValue(filterManager.filter(it))
        }
    }

    fun updateCurrentLocation(location: Location) {
        currentLocation = location
        if (sortedCheckedId.value == R.id.radioButton_filterFragment_distance) {
            sortList()
        }
    }

    private fun sortList() {
        publicWorkMediatedList.value?.let { publicWorkList ->
            publicWorkMediatedList.value =
                when (sortedCheckedId.value) {
                    R.id.radioButton_filterFragment_a_z -> publicWorkList.sortedBy { it.publicWork.name }
                    R.id.radioButton_filterFragment_z_a -> publicWorkList.sortedByDescending { it.publicWork.name }
                    R.id.radioButton_filterFragment_distance -> publicWorkList.sortedBy {
                        it.address.getLocation()?.distanceTo(currentLocation)
                    }
                    else -> publicWorkList
                }
        }
    }

    fun fromGeocoding(address: Address) {
        currentAddress.city = address.subAdminArea ?: ""
        currentAddress.street = address.thoroughfare ?: ""
        currentAddress.neighborhood = address.subLocality ?: ""
        currentAddress.number = address.subThoroughfare ?: ""
        currentAddress.cep = address.postalCode ?: ""
    }
}