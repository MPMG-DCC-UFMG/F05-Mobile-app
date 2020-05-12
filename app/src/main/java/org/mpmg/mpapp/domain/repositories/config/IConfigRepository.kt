package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote

interface IConfigRepository {

    suspend fun loadTypeWorks(): List<TypeWorkRemote>

    suspend fun getTypeWorkVersion(): EntityVersion

    fun saveTypeWorksVersion(typeWorksVersion: Int)

    fun currentTypeWorksVersion(): Int

    fun setLoggedUserEmail(email: String)

    fun getLoggedUserEmail(): String
}