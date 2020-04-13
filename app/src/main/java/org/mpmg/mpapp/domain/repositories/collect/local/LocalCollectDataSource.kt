package org.mpmg.mpapp.domain.repositories.collect.local

import android.content.Context
import org.mpmg.mpapp.domain.models.Collect
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalCollectDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalCollectDataSource {

    override fun insertCollect(collect: Collect) {
        mpDatabase()!!.collectDAO().insert(collect)
    }

    override fun getCollectById(collectId: String): Collect? {
        return mpDatabase()!!.collectDAO().getCollectById(collectId)
    }

    override fun getCollectByPublicIdAndStatus(publicId: String, sent: Boolean): Collect? {
        return mpDatabase()!!.collectDAO().getCollectByPublicIdAndStatus(publicId, sent)
    }
}