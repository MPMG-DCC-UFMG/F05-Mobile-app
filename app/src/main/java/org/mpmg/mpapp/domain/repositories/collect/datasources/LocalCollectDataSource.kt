package org.mpmg.mpapp.domain.repositories.collect.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalCollectDataSource(applicationContext: Context) : BaseDataSource(applicationContext){

    fun insertCollect(collect: Collect) {
        mpDatabase()!!.collectDAO().insert(collect)
    }

    fun getCollectById(collectId: String): Collect? {
        return mpDatabase()!!.collectDAO().getCollectById(collectId)
    }

    fun getCollectByPublicIdAndStatus(publicId: String, sent: Boolean): Collect? {
        return mpDatabase()!!.collectDAO().getCollectByPublicIdAndStatus(publicId, sent)
    }

    fun markCollectSent(collectId: String) {
        val oldCollect = mpDatabase()!!.collectDAO().getCollectById(collectId)
        oldCollect?.let {
            it.isSent = true
            mpDatabase()!!.collectDAO().update(it)
        }
    }

    fun deleteCollectById(collectId: String) {
        mpDatabase()!!.collectDAO().deleteCollectById(collectId)
    }
}