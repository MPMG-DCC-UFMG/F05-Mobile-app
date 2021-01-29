package org.mpmg.mpapp.ui.screens.photo.models

import org.mpmg.mpapp.domain.database.models.Photo

data class PhotoBundle(
    val photo: Photo,
    val action: PhotoActions
)
