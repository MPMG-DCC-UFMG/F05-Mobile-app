package org.mpmg.mpapp.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.mpmg.mpapp.BR;
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.screens.publicwork.models.BottomMenu

data class MenuItemModel(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val type: BottomMenu
) : BaseObservable() {

    @get:Bindable
    var selected: Boolean = false
        set(value) {
            field = value
            itemColor = if (value) R.color.colorGreenMP else R.color.colorDivider
            notifyPropertyChanged(BR.selected)
        }

    @get:Bindable
    @ColorRes
    var itemColor: Int = R.color.colorDivider
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemColor)
        }
}