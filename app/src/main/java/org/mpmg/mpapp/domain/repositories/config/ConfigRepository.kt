package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote
import org.mpmg.mpapp.domain.repositories.config.datasources.ILocalConfigDataSource

class ConfigRepository(val mpApi: MPApi, val localConfigDataSource: ILocalConfigDataSource) :
    IConfigRepository {


    override suspend fun loadTypeWorks(): List<TypeWorkRemote> {
        return mpApi.loadTypeWorks()
    }

    override suspend fun getTypeWorkVersion(): EntityVersion {
        return mpApi.getTypeWorkVersion()
    }

    override fun saveTypeWorksVersion(typeWorksVersion: Int) {
        localConfigDataSource.saveTypeWorksVersion(typeWorksVersion)
    }

    override fun currentTypeWorksVersion(): Int {
        return localConfigDataSource.currentTypeWorksVersion()
    }

    override fun setLoggedUserEmail(email: String) {
        localConfigDataSource.setLoggedUserEmail(email)
    }

    override fun getLoggedUserEmail(): String {
        return localConfigDataSource.getLoggedUserEmail()
    }
}