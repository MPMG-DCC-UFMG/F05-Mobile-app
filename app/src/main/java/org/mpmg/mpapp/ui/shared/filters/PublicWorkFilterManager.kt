package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress

class PublicWorkFilterManager {

    var filters = mutableMapOf<String, IPublicWorkFilter>()

    fun addFilter(key: String, filter: IPublicWorkFilter) {
        filters[key] = filter
    }

    fun removeFilter(key: String) {
        filters.remove(key)
    }

    fun filter(list: List<PublicWorkAndAdress>): List<PublicWorkAndAdress> {
        if (filters.isEmpty()) return list

        return list.filter { publicWorkAddress ->
            var keep = true
            filters.forEach {
                if (!keep) return@forEach
                keep = keep && it.value.keepItem(publicWorkAddress.publicWork)
            }
            keep
        }
    }
}