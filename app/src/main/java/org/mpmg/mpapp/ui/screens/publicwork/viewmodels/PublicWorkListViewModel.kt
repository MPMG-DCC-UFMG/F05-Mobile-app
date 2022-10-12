package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import android.location.Location
import androidx.lifecycle.*
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.repositories.inspections.InspectionsRepository
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.shared.filters.*

class PublicWorkListViewModel(
    publicWorkRepository: PublicWorkRepository,
    typeWorkRepository: TypeWorkRepository,
    workStatusRepository: WorkStatusRepository,
//    private var inspectionsRepository: InspectionRepository
    private var inspectionsRepository: InspectionsRepository
) : MVVMViewModel() {

    private val publicWorkList = publicWorkRepository.listAllPublicWorksLive().asLiveData()
//    private val inspectionList = inspectionsRepository.listAllInspectionsLive().asLiveData()

    var typeWorkList = typeWorkRepository.listAllTypeWorksLive().asLiveData()
    var workStatusList = workStatusRepository.listAllWorkStatus().asLiveData()

    private val _publicWorkMediatedList = MediatorLiveData<List<PublicWorkAndAddress>>()
    val publicWorkMediatedList: LiveData<List<PublicWorkAndAddress>> = _publicWorkMediatedList

    private val filterManager = PublicWorkFilterManager()
    private val filterSyncStatus = FilterSyncStatus()
    private val filterTypeWork = FilterTypeOfWork()
    private val filterWorkStatus = FilterWorkStatus()
    private val filterByName = FilterByName()

    private var currentLocation: Location? = null

    val query: MutableLiveData<String> = MutableLiveData<String>()
    val sortedCheckedId: MutableLiveData<Int> = MutableLiveData<Int>()

    init {
        filterManager.addFilters(
            listOf(
                filterWorkStatus,
                filterSyncStatus,
                filterByName,
                filterTypeWork
            )
        )

        filterSyncStatus.addSyncStatus(SyncStatus.COLLECTED)
        filterSyncStatus.addSyncStatus(SyncStatus.TO_SEND)

        sortedCheckedId.postValue(R.id.radioButton_filterFragment_a_z)
        initMediator()
    }

    fun updateCurrentLocation(location: Location) {
        currentLocation = location
        if (sortedCheckedId.value == R.id.radioButton_filterFragment_distance) {
            sortList()
        }
    }

    fun getFilteredTypeOfWorks(options: Array<Int>): BooleanArray {
        val isCheckedOption = mutableListOf<Boolean>()
        options.forEach { option ->
            isCheckedOption.add(filterTypeWork.isTypeOfWorkChecked(option))
        }

        return isCheckedOption.toBooleanArray()
    }

    fun getFilteredWorkStatus(options: Array<Int>): BooleanArray {
        val isCheckedOption = mutableListOf<Boolean>()
        options.forEach { option ->
            isCheckedOption.add(filterWorkStatus.isWorkStatusChecked(option))
        }

        return isCheckedOption.toBooleanArray()
    }

    private fun initMediator() {

        _publicWorkMediatedList.addSource(publicWorkList) { result ->
            result?.let {
                _publicWorkMediatedList.value = filterManager.filter(it)
                sortList()
            }
        }

        _publicWorkMediatedList.addSource(query) { search ->
            filterByName.query = search ?: ""
            publicWorkList.value?.let {
                _publicWorkMediatedList.postValue(filterManager.filter(it))
            }
        }

        _publicWorkMediatedList.addSource(sortedCheckedId) {
            sortList()
        }
    }

    private fun sortList() {
        _publicWorkMediatedList.value?.let { publicWorkList ->
            _publicWorkMediatedList.postValue(
                when (sortedCheckedId.value) {
                    R.id.radioButton_filterFragment_a_z -> publicWorkList.sortedBy { it.publicWork.name }
                    R.id.radioButton_filterFragment_z_a -> publicWorkList.sortedByDescending { it.publicWork.name }
                    R.id.radioButton_filterFragment_distance -> {
                        currentLocation?.let {
                            publicWorkList.sortedBy {
                                it.address.getLocation()?.distanceTo(currentLocation)
                            }
                        } ?: publicWorkList
                    }
                    else -> publicWorkList
                }
            )
        }
    }

    fun isSyncStatusChecked(syncStatus: SyncStatus): Boolean {
        return filterSyncStatus.isStatusEnabled(syncStatus)
    }

    fun updateSyncStatusFilter(syncStatus: SyncStatus, checked: Boolean) {
        if (checked) {
            filterSyncStatus.addSyncStatus(syncStatus)
        } else {
            filterSyncStatus.removeSyncStatus(syncStatus)
        }
        filter()
    }

    fun updateTypeWorkFilter(selectedTypeWorkIndex: List<Int>) {
        filterTypeWork.setTypeOfWorks(selectedTypeWorkIndex)
        filter()
    }

    fun updateWorkStatusFilter(selectedWorkStatusIndex: List<Int>) {
        filterWorkStatus.setWorkStatus(selectedWorkStatusIndex)
        filter()
    }

    private fun filter() {
        publicWorkList.value?.let {
            _publicWorkMediatedList.postValue(filterManager.filter(it))
        }
    }

//    suspend fun retrieveInspections(publicWorkId: String) = inspectionsRepository.listAllInspections()//retrieveInspectionsByPublicWorkId(publicWorkId)
    suspend fun retrieveInspections(publicWorkId: String) = inspectionsRepository.retrieveInspectionsByPublicWorkId(publicWorkId)
}