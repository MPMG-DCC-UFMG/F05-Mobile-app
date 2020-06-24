package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.database.models.PublicWork

class FilterTypeOfWork : IPublicWorkFilter() {

    override val filterKey: String
        get() = this::class.java.name

    var selectedTypeOfWorks = mutableSetOf<Int>()

    fun addTypeOfWork(type: Int) {
        selectedTypeOfWorks.add(type)
    }

    fun removeTypeOfWork(type: Int) {
        selectedTypeOfWorks.remove(type)
    }

    fun setTypeOfWorks(types: List<Int>) {
        selectedTypeOfWorks.clear()
        selectedTypeOfWorks.addAll(types)
    }

    override fun keepItem(publicWork: PublicWork): Boolean {
        if (selectedTypeOfWorks.isEmpty()) return true
        return selectedTypeOfWorks.contains(publicWork.typeWorkFlag)
    }

    fun isTypeOfWorkChecked(type: Int): Boolean {
        return selectedTypeOfWorks.contains(type)
    }
}