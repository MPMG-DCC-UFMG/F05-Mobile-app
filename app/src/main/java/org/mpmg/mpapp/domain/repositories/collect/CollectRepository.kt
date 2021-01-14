package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.repositories.collect.datasources.LocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.LocalPhotoDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.RemoteCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.RemotePhotoDataSource
import java.io.File

class CollectRepository(
    private val localPhotoDataSource: LocalPhotoDataSource,
    private val localCollectDataSource: LocalCollectDataSource,
    private val remoteCollectDataSource: RemoteCollectDataSource,
    private val remotePhotoDataSource: RemotePhotoDataSource
) {

    fun listPhotosByCollectionIDLive(collectionId: String): Flow<List<Photo>> {
        return localPhotoDataSource.listPhotosByCollectionIDLive(collectionId)
    }

    fun getPhotoByID(photoId: String): Photo? {
        return localPhotoDataSource.getPhotoByID(photoId)
    }

    fun insertPhotos(photos: List<Photo>) {
        localPhotoDataSource.insertPhotos(photos)
    }

    @Transaction
    fun insertCollect(collect: Collect, photos: List<Photo>) {
        localCollectDataSource.insertCollect(collect)
        localPhotoDataSource.insertPhotos(photos)
    }

    fun getCollect(collectId: String): Collect? {
        return localCollectDataSource.getCollectById(collectId)
    }

    fun getCollectByPublicWork(publicWorkId: String): Collect? {
        return localCollectDataSource.getCollectByPublicIdAndStatus(publicWorkId, false)
    }

    fun listPhotosByCollectionID(collectionId: String): List<Photo> {
        return localPhotoDataSource.listPhotosByCollectionID(collectionId)
    }

    fun deletePhotoById(photoId: String) {
        localPhotoDataSource.deletePhotoById(photoId)
    }

    suspend fun sendCollect(collectRemote: CollectRemote): ResponseRemote {
        return remoteCollectDataSource.sendCollect(collectRemote)
    }

    suspend fun sendImage(image: File): ImageUploadResponse {
        return remotePhotoDataSource.sendImage(image)
    }

    suspend fun sendPhoto(photo: PhotoRemote): ResponseRemote {
        return remotePhotoDataSource.sendPhoto(photo)
    }

    fun markCollectSent(collectId: String) {
        localCollectDataSource.markCollectSent(collectId)
    }

    fun deleteCollectById(collectId: String) {
        val photos = localPhotoDataSource.listPhotosByCollectionID(collectId)
        photos.forEach {
            deletePhotoById(photoId = it.id)
        }
        localCollectDataSource.deleteCollectById(collectId)
    }
}