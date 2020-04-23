package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.models.PublicWork

class FilterByName : IPublicWorkFilter() {

    var query: String = ""

    override val filterKey: String
        get() = this::class.java.name

    override fun keepItem(publicWork: PublicWork): Boolean {
        if (query.isBlank()) return true
        return publicWork.name.contains(query)
    }
}