package org.mpmg.mpapp.ui.screens.inspection.models

data class ItemInspectionList(
    val inspectionTitle: String,
    val inspectionNumber: Number,
    val inspectionAddress: String,
    val inspectionStatus: Boolean,
    val inspectionSync: Boolean,
    val publicWorkId: String
)

class InspectionDataBuilder {
    var inspectionTitle: String = ""
    var inspectionNumber: Number = 0
    var inspectionAddress: String = ""
    var inspectionStatus: Boolean = false
    var inspectionSync: Boolean = false
    var publicWorkId: String = ""

    fun build(): ItemInspectionList =
        ItemInspectionList(
            inspectionTitle,
            inspectionNumber,
            inspectionAddress,
            inspectionStatus,
            inspectionSync,
            publicWorkId
        )
}

fun itemSurveyList(block: InspectionDataBuilder.() -> Unit): ItemInspectionList =
    InspectionDataBuilder().apply(block).build()

fun fakeSurveys(): MutableList<ItemInspectionList> = mutableListOf(
    itemSurveyList {
        inspectionTitle = "Pavimentação"
        inspectionNumber = 22658
        inspectionAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        inspectionStatus = true
        inspectionSync = true
    },
    itemSurveyList {
        inspectionTitle = "Ponte"
        inspectionNumber = 26573
        inspectionAddress = "Rua liberdade, Centro - Poços de Caldas"
        inspectionStatus = true
        inspectionSync = true
    },
    itemSurveyList {
        inspectionTitle = "Obra de arte"
        inspectionNumber = 21584
        inspectionAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        inspectionStatus = true
        inspectionSync = true
    },
    itemSurveyList {
        inspectionTitle = "Pavimentação"
        inspectionNumber = 22658
        inspectionAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        inspectionStatus = true
        inspectionSync = true
    },
    itemSurveyList {
        inspectionTitle = "Ponte"
        inspectionNumber = 26573
        inspectionAddress = "Rua liberdade, Centro - Poços de Caldas"
        inspectionStatus = true
        inspectionSync = true
    },
    itemSurveyList {
        inspectionTitle = "Obra de arte"
        inspectionNumber = 21584
        inspectionAddress = "Rua leonor santos, Boa Vista - Belo Horizonte"
        inspectionStatus = true
        inspectionSync = true
    },

    )