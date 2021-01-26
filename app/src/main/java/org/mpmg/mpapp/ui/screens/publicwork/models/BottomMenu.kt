package org.mpmg.mpapp.ui.screens.publicwork.models

import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.components.MenuItemModel

enum class BottomMenu {
    HOME {
        override fun getMenu(): MenuItemModel = MenuItemModel(
            icon = R.drawable.ic_home,
            title = R.string.menu_home,
            type = HOME
        )
    },
    LIST {
        override fun getMenu(): MenuItemModel = MenuItemModel(
            icon = R.drawable.ic_list,
            title = R.string.menu_list,
            type = LIST
        )
    },
    FILTER {
        override fun getMenu(): MenuItemModel = MenuItemModel(
            icon = R.drawable.ic_filter,
            title = R.string.menu_filter,
            type = FILTER
        )
    };

    abstract fun getMenu(): MenuItemModel
}