package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

class PublicWorkFilterManager {

    private var filters = mutableMapOf<String, IPublicWorkFilter>()

    fun addFilter(key: String, filter: IPublicWorkFilter) {
        filters[key] = filter
    }

    fun removeFilter(key: String) {
        filters.remove(key)
    }

    fun filter(list: List<PublicWorkAndAddress>): List<PublicWorkAndAddress> {
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