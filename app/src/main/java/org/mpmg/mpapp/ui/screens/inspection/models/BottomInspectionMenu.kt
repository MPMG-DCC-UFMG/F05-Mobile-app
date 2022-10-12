package org.mpmg.mpapp.ui.screens.inspection.models

import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.components.MenuInspectionItemModel

enum class BottomInspectionMenu {
    HOME {
        override fun getMenu(): MenuInspectionItemModel = MenuInspectionItemModel(
            icon = R.drawable.ic_home,
            title = R.string.menu_home,
            type = HOME
        )
    },
    LIST {
        override fun getMenu(): MenuInspectionItemModel = MenuInspectionItemModel(
            icon = R.drawable.ic_list,
            title = R.string.menu_list,
            type = LIST
        )
    },
    FILTER {
        override fun getMenu(): MenuInspectionItemModel = MenuInspectionItemModel(
            icon = R.drawable.ic_filter,
            title = R.string.menu_filter,
            type = FILTER
        )
    },
    MAP{
        override fun getMenu(): MenuInspectionItemModel = MenuInspectionItemModel(
            icon = R.drawable.ic_map_marker,
            title = R.string.menu_map,
            type = MAP
        )
    };

    abstract fun getMenu(): MenuInspectionItemModel
}