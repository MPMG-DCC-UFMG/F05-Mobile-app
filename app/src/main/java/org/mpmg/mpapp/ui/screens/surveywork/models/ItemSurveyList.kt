package org.mpmg.mpapp.ui.screens.surveywork.models

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import org.mpmg.mpapp.R

data class ItemSurveyList(
    val surveyTitle: String,
    val surveyNumber: Number,
    val surveyAddress: String,
    val surveyStatus: Boolean,
    val surveySync: Boolean
)

class SurveyDataBuilder {
    var surveyTitle: String = ""
    var surveyNumber: Number = 0
    var surveyAddress: String = ""
    var surveyStatus: Boolean = false
    var surveySync: Boolean = false

    fun build(): ItemSurveyList = ItemSurveyList(surveyTitle, surveyNumber, surveyAddress, surveyStatus, surveySync)
}

fun itemSurveyList(block: SurveyDataBuilder.() -> Unit): ItemSurveyList = SurveyDataBuilder().apply(block).build()

fun fakeSurveys(): MutableList<ItemSurveyList> = mutableListOf(
    itemSurveyList {
        surveyTitle = "Pavimentação"
        surveyNumber = 22658
        surveyAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        surveyStatus = true
        surveySync = true
    },
    itemSurveyList {
        surveyTitle = "Ponte"
        surveyNumber = 26573
        surveyAddress = "Rua liberdade, Centro - Poços de Caldas"
        surveyStatus = true
        surveySync = true
    },
    itemSurveyList {
        surveyTitle = "Obra de arte"
        surveyNumber = 21584
        surveyAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        surveyStatus = true
        surveySync = true
    },
    itemSurveyList {
        surveyTitle = "Pavimentação"
        surveyNumber = 22658
        surveyAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        surveyStatus = true
        surveySync = true
    },
    itemSurveyList {
        surveyTitle = "Ponte"
        surveyNumber = 26573
        surveyAddress = "Rua liberdade, Centro - Poços de Caldas"
        surveyStatus = true
        surveySync = true
    },
    itemSurveyList {
        surveyTitle = "Obra de arte"
        surveyNumber = 21584
        surveyAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        surveyStatus = true
        surveySync = true
    },

)