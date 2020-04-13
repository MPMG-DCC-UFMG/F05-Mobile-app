package org.mpmg.mpapp.domain.repositories.collect.local

import org.mpmg.mpapp.domain.models.Collect

interface ILocalCollectDataSource {

    fun insertCollect(collect: Collect)

    fun getCollectById(collectId: String): Collect?

    fun getCollectByPublicIdAndStatus(publicId: String, sent: Boolean): Collect?
}