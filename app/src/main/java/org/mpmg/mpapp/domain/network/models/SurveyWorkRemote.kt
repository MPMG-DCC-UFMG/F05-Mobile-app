package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.domain.database.models.TypeSurvey

data class SurveyWorkRemote(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "flag") val flag: Int,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "public_work_id") val public_work_id: Int,
    @field:Json(name = "collect_id") val collect_id: Int,
    @field:Json(name = "status") val statusList: List<Int>,
    @field:Json(name = "user_status") val user_status: Int
) {
    fun toTypeSurveyDB(): TypeSurvey {
        val parsedList = statusList.joinToString(",")
        return TypeSurvey(
            name = name,
            flag = flag,
            description = description,
            public_work_id = public_work_id,
            collect_id = collect_id,
            status = parsedList,
            user_status = user_status
        )
    }
}