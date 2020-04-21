package org.mpmg.mpapp.domain.network

import org.mpmg.mpapp.domain.models.TypeWork

class MPApi {

    fun loadTypeWorkAPI(): List<TypeWork> {
        return listOf(TypeWork(flag = 1, name = "Escola"), TypeWork(flag = 2, name = "Creche"))
    }

    fun getConfigFilesVersion(): Int {
        return 2
    }
}