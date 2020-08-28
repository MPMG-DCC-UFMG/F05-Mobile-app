package org.mpmg.mpapp.domain.repositories.collect.datasources

import org.mpmg.mpapp.domain.database.models.Collect

interface ILocalCollectDataSource {

    fun insertCollect(collect: Collect)

    fun getCollectById(collectId: String): Collect?

    fun getCollectByPublicIdAndStatus(publicId: String, sent: Boolean): Collect?

    fun markCollectSent(collectId: String)

    fun deleteCollectById(collectId: String)

}