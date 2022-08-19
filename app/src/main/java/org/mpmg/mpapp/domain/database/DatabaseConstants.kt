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
        const val typeWorkFlag = "public_work_type_work_flag"
        const val idCollect = "public_work_id_collect"
        const val toSend = "public_work_to_send"
        const val userStatusFlag = "public_work_user_status_flag"
    }

    object TypeWork {
        const val tableName = "type_work_table"
        const val flag = "type_work_flag"
        const val name = "type_work_name"
        const val status = "type_work_status"
    }

    object TypeSurvey {
        const val tableName = "type_survey_table"
        const val flag = "type_survey_flag"
        const val name = "type_survey_name"
        const val description = "type_survey_description"
        const val public_work_id = "type_survey_public_work_id"
        const val collect_id = "type_survey_collect_id"
        const val status = "type_survey_collect_status"
        const val user_status = "type_survey_collect_user_status"
    }

    object TypePhoto {
        const val tableName = "type_photo_table"
        const val flag = "type_photo_flag"
        const val name = "type_photo_name"
        const val description = "type_photo_description"
    }

    object Collect {
        const val tableName = "collect_table"
        const val id = "collect_id"
        const val idUser = "collect_user_id"
        const val date = "collect_date"
        const val comments = "collect_comments"
        const val isSent = "collect_is_sent"
        const val idPublicWork = "collect_public_work_id"
        const val publicWorkStatus = "collect_public_work_status"
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
        const val filepath = "photo_filepath"
        const val type = "photo_type"
    }

    object Address {
        const val tableName = "address_table"
        const val id = "address_id"
        const val street = "address_street"
        const val neighborhood = "address_neighborhood"
        const val number = "address_number"
        const val city = "address_city"
        const val state = "address_state"
        const val latitude = "address_latitude"
        const val longitude = "address_longitude"
        const val cep = "address_cep"
        const val idPublicWork = "address_public_work_id"
    }

    object AssociationTWTP {
        const val tableName = "association_twtp_table"
        const val id = "association_twtp_id"
        const val typeWorkFlag = "association_twtp_type_work_flag"
        const val typePhotoFlag = "association_twtp_type_photo_flag"
    }

    object WorkStatus {
        const val tableName = "work_status_table"
        const val flag = "work_status_flag"
        const val name = "work_status_name"
        const val description = "work_status_description"
    }

    object City {
        const val tableName = "city_table"
        const val name = "city_name"
        const val uf = "city_uf"
        const val latitude = "city_latitude"
        const val longitude = "city_longitude"
        const val codigoIbge = "city_codigo_ibge"
    }
}