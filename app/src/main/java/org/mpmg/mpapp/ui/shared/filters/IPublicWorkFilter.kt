package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.models.PublicWork

abstract class IPublicWorkFilter {

    abstract val filterKey: String

    abstract fun keepItem(publicWork: PublicWork): Boolean
}