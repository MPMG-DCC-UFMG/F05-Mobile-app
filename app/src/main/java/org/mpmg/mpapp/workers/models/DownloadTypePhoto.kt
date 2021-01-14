package org.mpmg.mpapp.workers.models

import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.TypePhotoRemote
import org.mpmg.mpapp.domain.repositories.typephoto.TypePhotoRepository

class DownloadTypePhoto : BaseDownloadInfo<TypePhotoRemote>() {

    private val typePhotoRepository: TypePhotoRepository by inject()

    override fun resourceId(): Int = R.string.progress_type_photo

    override fun currentVersion(): Int = configRepository.currentTypePhotosVersion()

    override suspend fun serverVersion(): EntityVersion = configRepository.getTypePhotosVersion()

    override suspend fun loadInfo(): Array<TypePhotoRemote> =
        configRepository.loadTypePhotos().toTypedArray()

    override fun updateCurrentVersion(serverVersion: Int) =
        configRepository.saveTypePhotosVersion(serverVersion)

    override fun onSuccess(list: Array<*>): Boolean {
        return if (list.isArrayOf<TypePhotoRemote>()) {
            typePhotoRepository.deleteTypePhotos()
            typePhotoRepository.insertTypePhotos(list.map { typePhoto ->
                typePhoto as TypePhotoRemote
                typePhoto.toTypePhotoDB()
            })
            true
        } else {
            false
        }
    }
}