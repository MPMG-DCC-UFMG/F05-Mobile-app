package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.TypeWork

data class TypeWorkRemote(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "flag") val flag: Int,
    @field:Json(name = "status_list") val statusList: List<Int>
) {
    fun toTypeWorkDB(): TypeWork {
        val parsedList = statusList.joinToString(",")
        return TypeWork(
            name = name,
            flag = flag,
            status = parsedList
        )
    }
}