package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.ui.screens.publicwork.models.BottomMenu

class BottomMenuViewModel : ViewModel() {

    val navigationOptions = BottomMenu.values().map { it.getMenu() }

    var selectedOption = MutableLiveData<Pair<BottomMenu, Boolean>>()

    init {
        selectOption(BottomMenu.LIST,true)
    }

    fun selectOption(id: org.mpmg.mpapp.ui.screens.publicwork.models.BottomMenu, navigate: Boolean) {
        if (selectedOption.value?.first != id) {
            selectedOption.postValue(Pair(id, navigate))
        }
        updateSelectedOption(id)
    }

    fun updateSelectedOption(id: BottomMenu) {
        navigationOptions.forEach { option ->
            option.selected = option.type == id
        }
    }

}