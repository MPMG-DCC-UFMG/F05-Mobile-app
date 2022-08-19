package org.mpmg.mpapp.ui.screens.surveywork.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.ui.screens.surveywork.models.BottomSurveyMenu

class BottomSurveyMenuViewModel : ViewModel() {

    val navigationOptions = BottomSurveyMenu.values().map { it.getMenu() }

    var selectedOption = MutableLiveData<Pair<BottomSurveyMenu, Boolean>>()

    init {
        selectOption(BottomSurveyMenu.LIST,true)
    }

    fun selectOption(id: org.mpmg.mpapp.ui.screens.surveywork.models.BottomSurveyMenu, navigate: Boolean) {
        if (selectedOption.value?.first != id) {
            selectedOption.postValue(Pair(id, navigate))
        }
        updateSelectedOption(id)
    }

    fun updateSelectedOption(id: BottomSurveyMenu) {
        navigationOptions.forEach { option ->
            option.selected = option.type == id
        }
    }

}