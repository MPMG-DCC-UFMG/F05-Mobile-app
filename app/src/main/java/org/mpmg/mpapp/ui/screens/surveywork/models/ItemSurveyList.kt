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
    val surveySync: Boolean,
    val publicWorkId: String
)

class SurveyDataBuilder {
    var surveyTitle: String = ""
    var surveyNumber: Number = 0
    var surveyAddress: String = ""
    var surveyStatus: Boolean = false
    var surveySync: Boolean = false
    var publicWorkId: String = ""

    fun build(): ItemSurveyList =
        ItemSurveyList(
            surveyTitle,
            surveyNumber,
            surveyAddress,
            surveyStatus,
            surveySync,
            publicWorkId
        )
}

fun itemSurveyList(block: SurveyDataBuilder.() -> Unit): ItemSurveyList =
    SurveyDataBuilder().apply(block).build()