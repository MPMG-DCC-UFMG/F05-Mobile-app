package org.mpmg.mpapp.domain.database

object DatabaseConstants {

    object User {
        const val tableName = "user_table"
        const val id = "user_id"
        const val email = "user_email"
        const val name = "user_name"
        const val cpf = "user_cpf"
    }

    object PublicWork {
        const val tableName = "public_work_table"
        const val id = "public_work_id"
        const val name = "public_work_name"
        const val latitude = "public_work_latitude"
        const val longitude = "public_work_longitude"
        const val lastCollect = "public_work_last_collect"
        const val typeWorkFlag = "public_work_type_work_flag"
    }

    object TypeWork {
        const val tableName = "type_work_table"
        const val flag = "type_work_flag"
        const val name = "type_work_name"
    }

    object Collect {
        const val tableName = "collect_table"
        const val id = "collect_id"
        const val idWork = "collect_work_id"
        const val idUser = "collect_user_id"
        const val date = "collect_date"
        const val comments = "collect_comments"
        const val isSent = "collect_is_sent"
    }

    object Photo {
        const val tableName = "photo_table"
        const val id = "photo_id"
        const val idCollect = "photo_id_collect"
        const val isSent = "photo_is_sent"
        const val latitude = "photo_latitude"
        const val longitude = "photo_longitude"
        const val timestamp = "photo_timestamp"
        const val comment = "photo_comment"
        const val filename = "photo_filename"
    }
}