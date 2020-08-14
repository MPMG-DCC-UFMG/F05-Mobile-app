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
    }

    object TypeWork {
        const val tableName = "type_work_table"
        const val flag = "type_work_flag"
        const val name = "type_work_name"
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
}