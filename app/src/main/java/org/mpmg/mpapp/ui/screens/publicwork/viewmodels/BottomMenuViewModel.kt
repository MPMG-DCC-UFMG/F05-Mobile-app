package org.mpmg.mpapp.ui.screens.publicwork.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.ui.screens.publicwork.models.BottomMenu

class BottomMenuViewModel : ViewModel() {

    val navigationOptions = BottomMenu.values().map { it.getMenu() }

    var selectedOption = MutableLiveData<BottomMenu>()

    init {
        selectOption(BottomMenu.LIST)
    }

    fun selectOption(id: BottomMenu) {
        selectedOption.postValue(id)
    }

    fun updateSelectedOption(id: BottomMenu) {
        navigationOptions.forEach { option ->
            option.selected = option.type == id
        }
    }

}