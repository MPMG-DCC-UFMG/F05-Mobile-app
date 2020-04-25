package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.models.PublicWork

class FilterTypeOfWork : IPublicWorkFilter() {

    override val filterKey: String
        get() = this::class.java.name

    var selectedTypeOfWorks = mutableSetOf<Int>()

    fun addTypeOfWork(type: Int){
        selectedTypeOfWorks.add(type)
    }

    fun removeTypeOfWork(type: Int){
        selectedTypeOfWorks.remove(type)
    }

    override fun keepItem(publicWork: PublicWork): Boolean {
        if (selectedTypeOfWorks.isEmpty()) return true
        return selectedTypeOfWorks.contains(publicWork.typeWorkFlag)
    }

    fun isTypeOfWorkChecked(type: Int): Boolean {
        return selectedTypeOfWorks.contains(type)
    }
}