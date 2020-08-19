package org.mpmg.mpapp.domain.network.models

import com.squareup.moshi.Json
import org.mpmg.mpapp.ui.screens.login.models.CreateAccountUI

data class MPUserRemote(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "authentication") val authentication: String,
    @field:Json(name = "full_name") val fullName: String
) {
    constructor(createAccountUI: CreateAccountUI) : this(
        createAccountUI.email,
        createAccountUI.password1,
        createAccountUI.email
    )
}