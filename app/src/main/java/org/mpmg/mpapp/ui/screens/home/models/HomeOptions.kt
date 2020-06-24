package org.mpmg.mpapp.ui.screens.home.models

import org.mpmg.mpapp.core.interfaces.BaseModel

data class HomeOptions (
    val iconResourceId: Int,
    val optionTitle: String,
    var onClick: (() -> Unit)? = null
): BaseModel