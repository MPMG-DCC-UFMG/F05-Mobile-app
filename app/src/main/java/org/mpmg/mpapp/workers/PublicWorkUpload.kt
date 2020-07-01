package org.mpmg.mpapp.workers

import android.content.Context
import android.media.ExifInterface.TAG_IMAGE_DESCRIPTION
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.WORKER_PARAMETER_PUBLIC_WORK_ID
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import java.io.File

class PublicWorkUpload(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val TAG = PublicWorkUpload::class.java.name


    private val publicWorkRepository: IPublicWorkRepository by inject()
    private val collectRepository: ICollectRepository by inject()

    companion object {
        const val Progress = "Progress"
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {

        val publicWorkId =
            inputData.getString(WORKER_PARAMETER_PUBLIC_WORK_ID) ?: return Result.failure()

        updateProgress(0, getString(R.string.progress_loading_public_work))
        val publicWorkAndAddress = publicWorkRepository.getPublicWorkById(publicWorkId)

        publicWorkAndAddress?.let {
            if (publicWorkAndAddress.publicWork.toSend) {

                updateProgress(10, getString(R.string.progress_sending_public_work))

                kotlin.runCatching {
                    publicWorkRepository.sendPublicWork(PublicWorkRemote(it))
                }.onSuccess {
                    markPublicWorkSent(publicWorkAndAddress.publicWork)
                }.onFailure {
                    return Result.failure()
                }
            }
            updateProgress(20, getString(R.string.progress_public_work_sent))

            val collectId = publicWorkAndAddress.publicWork.idCollect
            if (collectId != null) {
                updateProgress(30, getString(R.string.progress_loading_collect))
                val collect = collectRepository.getCollect(collectId) ?: return Result.failure()
                updateProgress(40, getString(R.string.progress_sending_collect))
                kotlin.runCatching {
                    collectRepository.sendCollect(CollectRemote(collect))
                }.onFailure {
                    return Result.failure()
                }

                updateProgress(50, getString(R.string.progress_loading_photos))
                val photos = collectRepository.listPhotosByCollectionID(collect.id)
                val allPhotosUploaded = sendPhotos(photos, publicWorkAndAddress)

                if (allPhotosUploaded) {
                    markCollectSent(publicWorkAndAddress.publicWork, collectId)
                } else {
                    return Result.failure()
                }
            }
        }

        updateProgress(70, getString(R.string.progress_finishing_upload))
        updateProgress(100, getString(R.string.progress_success_upload))

        return Result.success()
    }

    private suspend fun sendPhotos(
        photos: List<Photo>,
        publicWorkAndAddress: PublicWorkAndAddress
    ): Boolean {
        var allPhotosUploaded = true
        photos.forEachIndexed { index, photo ->
            try {
                updateProgress(
                    60,
                    String.format(
                        getString(R.string.progress_sending_photo),
                        index + 1,
                        photos.size
                    )
                )
                photo.filepath?.let { filepath ->
                    addMetadata(filepath, photo, publicWorkAndAddress)
                    val image = File(filepath)
                    kotlin.runCatching {
                        collectRepository.sendImage(image)
                    }.onSuccess {
                        val result = runCatching {
                            collectRepository.sendPhoto(PhotoRemote(photo, it.filepath))
                        }
                        allPhotosUploaded = allPhotosUploaded && result.isSuccess
                    }.onFailure {
                        allPhotosUploaded = false
                    }
                }
            } catch (e: Exception) {
                allPhotosUploaded = false
            }
        }

        return allPhotosUploaded
    }

    private fun addMetadata(
        filePath: String,
        photo: Photo,
        publicWorkAndAddress: PublicWorkAndAddress
    ) {
        try {
            val exif = ExifInterface(filePath)
            exif.setLatLong(photo.latitude, photo.longitude)
            exif.setAttribute(
                TAG_IMAGE_DESCRIPTION,
                "Obra: ${publicWorkAndAddress.publicWork.name} Endereco: ${publicWorkAndAddress.address} "
            )
            exif.saveAttributes()
        } catch (e: Exception) {
            Log.d(TAG, "error: ${e.message}")
        }

    }

    private fun getString(resourceId: Int): String {
        return applicationContext.getString(resourceId)
    }

    private suspend fun updateProgress(progress: Int, message: String) {
        setProgress(workDataOf(Progress to progress, Message to message))
    }

    private fun markPublicWorkSent(publicWork: PublicWork) {
        publicWorkRepository.markPublicWorkSent(publicWorkId = publicWork.id)
    }

    private fun markCollectSent(publicWork: PublicWork, collectId: String) {
        publicWorkRepository.unlinkCollectFromPublicWork(publicWorkId = publicWork.id)
        collectRepository.markCollectSent(collectId)
    }
}