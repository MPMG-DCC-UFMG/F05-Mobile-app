package org.mpmg.mpapp.workers

import android.content.Context
import android.media.ExifInterface.TAG_IMAGE_DESCRIPTION
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.WORKER_PARAMETER_PUBLIC_WORK_ID
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import java.io.File
import java.lang.NullPointerException

class PublicWorkUploadWorker(applicationContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(applicationContext, parameters), KoinComponent {

    private val TAG = PublicWorkUploadWorker::class.java.name

    private val publicWorkRepository: PublicWorkRepository by inject()
    private val collectRepository: CollectRepository by inject()

    companion object {
        const val Progress = "Progress"
        const val Message = "Message"
    }

    override suspend fun doWork(): Result {

        val publicWorkId =
            inputData.getString(WORKER_PARAMETER_PUBLIC_WORK_ID) ?: return Result.failure()

        updateProgress(0, getString(R.string.progress_loading_public_work))
        val publicWorkAndAddress = publicWorkRepository.getPublicWorkById(publicWorkId)
        return try {
            publicWorkAndAddress?.let {
                if (publicWorkAndAddress.publicWork.toSend) {

                    updateProgress(10, getString(R.string.progress_sending_public_work))

                    kotlin.runCatching {
                        publicWorkRepository.sendPublicWork(PublicWorkRemote(it))
                    }.onSuccess {
                        Log.i("crhisn", "success")
                        if(it.success){
                            markPublicWorkSent(publicWorkAndAddress.publicWork)
                        }else{
                            return Result.failure()
                        }
                    }.onFailure {
                        Log.i("crhisn", it.message, it)
                        return Result.failure()
                    }
                }
                updateProgress(20, getString(R.string.progress_public_work_sent))

                updateProgress(30, getString(R.string.progress_loading_collect))
                val collect = getCollect(publicWorkAndAddress)
                if (collect != null) {
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
                        markCollectSent(publicWorkAndAddress.publicWork, collect.id)
                    } else {
                        return Result.failure()
                    }
                }
            }

            updateProgress(70, getString(R.string.progress_finishing_upload))
            updateProgress(100, getString(R.string.progress_success_upload))

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

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
                        var result = true
                        runCatching {
                            collectRepository.sendPhoto(PhotoRemote(photo, it.filepath))
                        }.onSuccess {
                            result = it.success
                        }.onFailure {
                            result = false
                        }
                        allPhotosUploaded = allPhotosUploaded && result
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

    private fun getCollect(publicWorkAndAddress: PublicWorkAndAddress): Collect? {
        return publicWorkAndAddress.publicWork.idCollect?.let {
            collectRepository.getCollect(it) ?: run {
                publicWorkRepository.unlinkCollectFromPublicWork(publicWorkAndAddress.publicWork.id)
                throw NullPointerException()
            }
        }
    }
}