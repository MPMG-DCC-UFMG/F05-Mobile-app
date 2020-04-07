package org.mpmg.mpapp.domain.apis

import org.mpmg.mpapp.domain.models.TypeWork

class MPApi {

    fun loadTypeWorkAPI(): List<TypeWork> {
        return listOf(TypeWork(flag = 1, name = "Escola"))
    }

    fun getConfigFilesVersion(): Int {
        return 1;
    }
}