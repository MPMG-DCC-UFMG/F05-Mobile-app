package org.mpmg.mpapp.ui.screens.inspection.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.ui.screens.inspection.models.BottomInspectionMenu

class BottomInspectionMenuViewModel : ViewModel() {

    val navigationOptions = BottomInspectionMenu.values().map { it.getMenu() }

    var selectedOption = MutableLiveData<Pair<BottomInspectionMenu, Boolean>>()

    init {
        selectOption(BottomInspectionMenu.LIST,true)
    }

    fun selectOption(id: org.mpmg.mpapp.ui.screens.inspection.models.BottomInspectionMenu, navigate: Boolean) {
        if (selectedOption.value?.first != id) {
            selectedOption.postValue(Pair(id, navigate))
        }
        updateSelectedOption(id)
    }

    fun updateSelectedOption(id: BottomInspectionMenu) {
        navigationOptions.forEach { option ->
            option.selected = option.type == id
        }
    }

}