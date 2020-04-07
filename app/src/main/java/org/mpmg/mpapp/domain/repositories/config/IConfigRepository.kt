package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.models.TypeWork

interface IConfigRepository {

    fun loadListTypeWorks(): List<TypeWork>

    fun getServerConfigFilesVersion(): Int

    fun saveConfigFilesVersion(configVersion: Int)

    fun currentFilesVersion(): Int
}