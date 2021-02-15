package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.database.models.PublicWork

class FilterWorkStatus: IPublicWorkFilter() {

    override val filterKey: String
        get() = this::class.java.name

    var selectedWorkStatus = mutableSetOf<Int>()

    fun addWorkStatus(status: Int) {
        selectedWorkStatus.add(status)
    }

    fun removeWorkStatus(status: Int) {
        selectedWorkStatus.remove(status)
    }

    fun setWorkStatus(status: List<Int>) {
        selectedWorkStatus.clear()
        selectedWorkStatus.addAll(status)
    }

    override fun keepItem(publicWork: PublicWork): Boolean {
        if (selectedWorkStatus.isEmpty()) return true
        return selectedWorkStatus.contains(publicWork.userStatusFlag)
    }

    fun isWorkStatusChecked(type: Int): Boolean {
        return selectedWorkStatus.contains(type)
    }
}